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

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpHeaders;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class BasicTest extends AbstractTest {
    private RosetteAPI api;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, getFreePort());
    private MockServerClient mockServer;

    private static int getFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            serverPort = socket.getLocalPort();
        } catch (IOException e) {
            fail("Failed to allocate a port");
        }
        assertNotEquals(0, serverPort);
        return serverPort;
    }

    @Test
    public void testHeaders() throws Exception {
        // for version check call
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/rest/v1/info"))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withStatusCode(200)
                        .withBody("{\"name\": \"Rosette API\", \"version\": \"1.1\", \"versionChecked\": true}", StandardCharsets.UTF_8));
        // for the actual ping test
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader("X-Foo", "Bar")
                .withHeader(HttpHeaders.USER_AGENT, RosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withStatusCode(200)
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8));
        api = new RosetteAPI("foo-key", String.format("http://localhost:%d/rest/v1", serverPort));
        api.addCustomHeader("X-Foo", "Bar");
        api.ping();
    }
}
