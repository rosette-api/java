/*
* Copyright 2017-2022 Basis Technology Corp.
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

import com.basistech.rosette.apimodel.AdmRequest;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SupportedLanguage;
import com.basistech.rosette.apimodel.SupportedLanguagePair;
import com.basistech.rosette.apimodel.SupportedLanguagePairsResponse;
import com.basistech.rosette.apimodel.SupportedLanguagesResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.dm.AnnotatedText;

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.TextDomain;
import com.basistech.util.TransliterationScheme;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Delay;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.ENTITIES_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.NAME_SIMILARITY_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.NAME_TRANSLATION_SERVICE_PATH;
import static java.util.concurrent.TimeUnit.SECONDS;

public class BasicTest extends AbstractTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, false, getFreePort());
    private MockServerClient mockServer;
    private HttpRosetteAPI api;

    public BasicTest() throws IOException {
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            serverPort = socket.getLocalPort();
        }
        assertNotEquals(0, serverPort);
        return serverPort;
    }

    // an indirect way to show that connection pooling works
    // with concurrent connections = 1, the time to complete requests becomes serial
    // so: run several requests in threads, assert that they're executed serially
    // then set concurrent connections = 5,
    // run several requests again, showing they're executed in parallel
    @Test
    public void testMultipleConnections() throws InterruptedException {
        int delayTime = 3;
        int numConnections = 5;

        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/info")
                .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8)
                        .withStatusCode(200)
                        .withDelay(new Delay(SECONDS, delayTime)));

        // "before" case - send off (numConnections) requests, expect them to run serially
        api = new HttpRosetteAPI.Builder().connectionConcurrency(1)
                .url(String.format("http://localhost:%d/rest/v1", serverPort)).build();

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

        assertTrue(d2.getTime() - d1.getTime() > delayTime * numConnections * 1000); // at least as long as the delay in the request

        api = new HttpRosetteAPI.Builder().connectionConcurrency(numConnections)
                .url(String.format("http://localhost:%d/rest/v1", serverPort))
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

        assertTrue(d2.getTime() - d1.getTime() < delayTime * numConnections * 1000); // less than (numConnections) serial requests
        assertTrue(d2.getTime() - d1.getTime() > delayTime * 1000); // but at least as long as one
    }
    @Test
    public void testHeaders() throws Exception {
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader("X-Foo", "Bar")
                .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withHeader("X-RosetteAPI-Concurrency", "5")
                        .withStatusCode(200)
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8));

        api = new HttpRosetteAPI.Builder()
                .key("foo-key")
                .url(String.format("http://localhost:%d/rest/v1", serverPort))
                .additionalHeader("X-Foo", "Bar")
                .build();
        var resp = api.ping();
        assertEquals("5", resp.getExtendedInformation().get("X-RosetteAPI-Concurrency"));
    }

    @Test
    public void testAdm() throws Exception {
        try (InputStream reqIns = getClass().getResourceAsStream("/adm-req.json");
             InputStream respIns = getClass().getResourceAsStream("/adm-resp.json")) {
            assertNotNull(respIns);
            mockServer.when(HttpRequest.request()
                    .withMethod("POST")
                    .withPath("/rest/v1/entities"))
                    .respond(HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(IOUtils.toString(respIns, StandardCharsets.UTF_8)));
            api = new HttpRosetteAPI.Builder()
                    .key("foo-key")
                    .url(String.format("http://localhost:%d/rest/v1", serverPort))
                    .build();
            AnnotatedText testData = ApiModelMixinModule.setupObjectMapper(
                    new ObjectMapper()).readValue(reqIns, AnnotatedText.class);
            AnnotatedText resp = api.perform(ENTITIES_SERVICE_PATH,
                    AdmRequest.builder().text(testData).build());
            assertEquals("Q100", resp.getEntities().get(0).getEntityId());
        }
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
        api = new HttpRosetteAPI.Builder()
                .key("foo-key")
                .url(String.format("http://localhost:%d/rest/v1", serverPort))
                .build();
        Response resp = api.ping();
        assertEquals("Bar", resp.getExtendedInformation().get("X-Foo"));
        Set<?> foos = (Set)resp.getExtendedInformation().get("X-FooMulti");
        assertTrue(foos.contains("Bar1"));
        assertTrue(foos.contains("Bar2"));
    }

    private static class ApiPinger extends Thread {
        HttpRosetteAPI api1;

        ApiPinger(HttpRosetteAPI api) {
            this.api1 = api;
        }

        @Override
        public void run() {
            try {
                api1.info();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testLanguageSupport() throws Exception {
        try (InputStream respIns = getClass().getResourceAsStream("/supported-languages.json")) {
            assertNotNull(respIns);
            mockServer.when(HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/rest/v1/entities/supported-languages"))
                    .respond(HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(IOUtils.toString(respIns, StandardCharsets.UTF_8)));
            api = new HttpRosetteAPI.Builder()
                    .key("foo-key")
                    .url(String.format("http://localhost:%d/rest/v1", serverPort))
                    .build();
            SupportedLanguagesResponse resp = api.getSupportedLanguages(ENTITIES_SERVICE_PATH);
            assertEquals(2, resp.getSupportedLanguages().size());
            assertTrue(resp.getSupportedLanguages().contains(SupportedLanguage.builder()
                    .language(LanguageCode.ENGLISH)
                    .script(ISO15924.Latn)
                    .licensed(Boolean.TRUE)
                    .build()));
            assertTrue(resp.getSupportedLanguages().contains(SupportedLanguage.builder()
                    .language(LanguageCode.JAPANESE)
                    .script(ISO15924.Kana)
                    .licensed(Boolean.FALSE)
                    .build()));
        }
    }

    @Test
    public void testNameSimilarityLanguageSupport() throws Exception {
        try (InputStream respIns = getClass().getResourceAsStream("/name-similarity-supported-languages.json")) {
            assertNotNull(respIns);
            mockServer.when(HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/rest/v1/name-similarity/supported-languages"))
                    .respond(HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(IOUtils.toString(respIns, StandardCharsets.UTF_8)));
            api = new HttpRosetteAPI.Builder()
                    .key("foo-key")
                    .url(String.format("http://localhost:%d/rest/v1", serverPort))
                    .build();

            SupportedLanguagePairsResponse resp = api.getSupportedLanguagePairs(NAME_SIMILARITY_SERVICE_PATH);
            assertEquals(2, resp.getSupportedLanguagePairs().size());
            assertTrue(resp.getSupportedLanguagePairs().contains(SupportedLanguagePair.builder()
                    .source(new TextDomain(ISO15924.Latn, LanguageCode.ENGLISH, null))
                    .target(new TextDomain(ISO15924.Latn, LanguageCode.ENGLISH, null))
                    .licensed(Boolean.TRUE)
                    .build()));
            assertTrue(resp.getSupportedLanguagePairs().contains(SupportedLanguagePair.builder()
                    .source(new TextDomain(ISO15924.Arab, LanguageCode.ARABIC, null))
                    .target(new TextDomain(ISO15924.Arab, LanguageCode.ARABIC, null))
                    .licensed(Boolean.TRUE)
                    .build()));
        }
    }

    @Test
    public void testNameTranslationLanguageSupport() throws Exception {
        try (InputStream respIns = getClass().getResourceAsStream("/name-translation-supported-languages.json")) {
            assertNotNull(respIns);
            mockServer.when(HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/rest/v1/name-translation/supported-languages"))
                    .respond(HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(IOUtils.toString(respIns, StandardCharsets.UTF_8)));
            api = new HttpRosetteAPI.Builder()
                    .key("foo-key")
                    .url(String.format("http://localhost:%d/rest/v1", serverPort))
                    .build();

            SupportedLanguagePairsResponse resp = api.getSupportedLanguagePairs(NAME_TRANSLATION_SERVICE_PATH);
            assertEquals(2, resp.getSupportedLanguagePairs().size());
            assertTrue(resp.getSupportedLanguagePairs().contains(SupportedLanguagePair.builder()
                    .source(new TextDomain(ISO15924.Latn, LanguageCode.ENGLISH, TransliterationScheme.NATIVE))
                    .target(new TextDomain(ISO15924.Latn, LanguageCode.ENGLISH, TransliterationScheme.IC))
                    .licensed(Boolean.TRUE)
                    .build()));
            assertTrue(resp.getSupportedLanguagePairs().contains(SupportedLanguagePair.builder()
                    .source(new TextDomain(ISO15924.Arab, LanguageCode.ARABIC, TransliterationScheme.NATIVE))
                    .target(new TextDomain(ISO15924.Arab, LanguageCode.ARABIC, TransliterationScheme.NATIVE))
                    .licensed(Boolean.TRUE)
                    .build()));
        }
    }
}
