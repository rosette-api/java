/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.Response;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Delay;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("deprecation")
public class OldBasicTest extends AbstractTest {
    private RosetteAPI api;

    private MockServerClient mockServer;
    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, getFreePort());

    private static int getFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            serverPort = socket.getLocalPort();
        } catch (IOException e) {
            fail("Failed to allocate a port");
        }
        assertNotEquals(0, serverPort);
        return serverPort;
    }

    @Before
    public void setup() {
        mockServer.reset();
        // for version check call
    }

    // an indirect way to show that connection pooling works
    // with concurrent connections = 1, the time to complete requests becomes serial
    // so: run several requests in threads, assert that they're executed serially
    // then set concurrent connections = 5,
    // run several requests again, showing they're executed in parallel
    @Test
    public void testMultipleConnections() throws IOException, RosetteAPIException, InterruptedException {
        int delayTime = 3;
        int numConnections = 5;

        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/info")
                .withHeader(HttpHeaders.USER_AGENT, RosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8)
                        .withStatusCode(200)
                        .withDelay(new Delay(SECONDS, delayTime)));

        // "before" case - send off (numConnections) requests, expect them to run serially
        api = new RosetteAPI.Builder().connectionConcurrency(1)
                .alternateUrl(String.format("http://localhost:%d/rest/v1", serverPort)).build();

        Date d1 = new Date();

        List<ApiPinger> pingers = new ArrayList<>();
        for (int i = 0; i < numConnections; i++) {
            pingers.add(new ApiPinger(api));
            pingers.get(i).start();
        }

        for (int i = 0; i < numConnections; i++) {
            pingers.get(i).join();
        }

        Date d2 = new Date();

        assert d2.getTime() - d1.getTime() > delayTime * numConnections * 1000; // at least as long as the delay in the request

        api = new RosetteAPI.Builder().connectionConcurrency(numConnections)
                .alternateUrl(String.format("http://localhost:%d/rest/v1", serverPort))
                .build();
        d1 = new Date();

        pingers = new ArrayList<>();
        for (int i = 0; i < numConnections; i++) {
            pingers.add(new ApiPinger(api));
            pingers.get(i).start();
        }

        for (int i = 0; i < numConnections; i++) {
            pingers.get(i).join();
        }

        d2 = new Date();

        assert d2.getTime() - d1.getTime() < delayTime * numConnections * 1000; // less than (numConnections) serial requests
        assert d2.getTime() - d1.getTime() > delayTime * 1000; // but at least as long as one
    }

    @Test
    public void testHeaders() throws Exception {
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader("X-Foo", "Bar")
                .withHeader(HttpHeaders.USER_AGENT, RosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withHeader("X-RosetteAPI-Concurrency", "5")
                        .withStatusCode(200)
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8));

        api = new RosetteAPI.Builder()
                .apiKey("foo-key")
                .alternateUrl(String.format("http://localhost:%d/rest/v1", serverPort))
                .withCustomHeader("X-Foo", "Bar")
                .build();
        api.ping();
    }

    @Test
    public void testExtendedInfo() throws Exception {
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("X-Foo", "Bar")
                        .withHeader("X-FooMulti", "Bar1", "Bar2")
                        .withHeader("X-RosetteAPI-Concurrency", "5")
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8));
        api = new RosetteAPI("foo-key", String.format("http://localhost:%d/rest/v1", serverPort));
        Response resp = api.ping();
        assertEquals("Bar", resp.getExtendedInformation().get("X-Foo"));
        Set<?> foos = (Set)resp.getExtendedInformation().get("X-FooMulti");
        assertTrue(foos.contains("Bar1"));
        assertTrue(foos.contains("Bar2"));
    }

    private class ApiPinger extends Thread {
        RosetteAPI api1;

        ApiPinger(RosetteAPI api) throws IOException, RosetteAPIException {
            this.api1 = api;
        }

        @Override
        public void run() {
            try {
                api1.info();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RosetteAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
