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

import com.basistech.rosette.api.common.AbstractRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockServerExtension.class)
class InvalidErrorTest {
    private MockServerClient mockServer;

    @BeforeEach
    public void setup(MockServerClient mockServer) {
        this.mockServer = mockServer;
    }

    @Test
    void notJsonError() throws Exception {
        mockServer.when(HttpRequest.request().withPath(".*/{2,}.*"))
                .respond(HttpResponse.response()
                            .withBody("Invalid path; '//'")
                            .withHeader("X-RosetteAPI-Concurrency", "5")
                            .withStatusCode(HTTP_NOT_FOUND));
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8)
                        .withStatusCode(HTTP_OK)
                        .withHeader("X-RosetteAPI-Concurrency", "5"));
        String mockServiceUrl = "http://localhost:" + mockServer.getPort() + "/rest//v1";
        boolean exceptional = false;
        try {
            HttpRosetteAPI api = new HttpRosetteAPI.Builder().key("my-key-123").url(mockServiceUrl).build();
            api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, DocumentRequest.builder().content("sample text").build(), LanguageResponse.class);
            api.close();
        } catch (HttpRosetteAPIException e) {
            exceptional = true;
            assertEquals("invalidErrorResponse", e.getErrorResponse().getCode());
            assertEquals(Integer.valueOf(404), e.getHttpStatusCode());
            assertNotNull(e.getErrorResponse().getMessage());
        }
        assertTrue(exceptional);
        mockServer.close();
    }
}
