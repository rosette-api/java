/*
* Copyright 2017 Basis Technology Corp.
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

import com.basistech.rosette.api.common.AbstractRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.nio.charset.StandardCharsets;


public class InvalidErrorTest extends AbstractTest {

    @Test
    public void notJsonError() throws Exception {
        MockServerClient mockServer = new MockServerClient("localhost", serverPort);
        mockServer.reset()
                .when(HttpRequest.request().withPath(".*/{2,}.*"))
                .respond(HttpResponse.response()
                            .withBody("Invalid path; '//'")
                            .withHeader("X-RosetteAPI-Concurrency", "5")
                            .withStatusCode(404));
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8)
                        .withStatusCode(200)
                        .withHeader("X-RosetteAPI-Concurrency", "5"));
        String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest//v1";
        boolean exceptional = false;
        try {
            HttpRosetteAPI api = new HttpRosetteAPI.Builder().key("my-key-123").url(mockServiceUrl).build();
            api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, DocumentRequest.builder().content("sample text").build(), LanguageResponse.class);
        } catch (HttpRosetteAPIException e) {
            exceptional = true;
            assertEquals("invalidErrorResponse", e.getErrorResponse().getCode());
            assertEquals(Integer.valueOf(404), e.getHttpStatusCode());
            assertNotNull(e.getErrorResponse().getMessage());
        }
        assertTrue(exceptional);
    }
}
