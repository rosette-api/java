/*
 * Copyright 2023 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockServerExtension.class)
class RosetteRequestTest {
    private MockServerClient mockServer;
    private HttpRosetteAPI api;

    @BeforeEach
    void setup(MockServerClient mockServer) {
        this.mockServer = mockServer;
    }

    private void setupResponse(String requestPath, String responseString, int statusCode, int delayMillis, int requestTimes) {
        this.mockServer.when(HttpRequest.request().withPath(requestPath), Times.exactly(requestTimes))
                .respond(HttpResponse.response()
                        .withHeader("Content-Type", "application/json")
                        .withHeader("X-RosetteAPI-Concurrency", "5")
                        .withStatusCode(statusCode)
                        .withBody(responseString)
                        .withDelay(TimeUnit.MILLISECONDS, delayMillis));
    }


    @Test
    void successfulRequest() throws ExecutionException, InterruptedException {
        //Api client setup
        this.api = new HttpRosetteAPI.Builder().url(String.format("http://localhost:%d/rest/v1", mockServer.getPort())).build();

        //response setup
        String entitiesResponse = "{\"entities\" : [ {     \"type\" : \"ORGANIZATION\",     \"mention\" : \"Securities and Exchange Commission\",     \"normalized\" : \"U.S. Securities and Exchange Commission\",     \"count\" : 1,     \"mentionOffsets\" : [ {       \"startOffset\" : 4,       \"endOffset\" : 38     } ],     \"entityId\" : \"Q953944\",     \"confidence\" : 0.39934742,     \"linkingConfidence\" : 0.67404154   } ] }";
        setupResponse("/rest/v1/entities", entitiesResponse, 200, 0, 1);

        //request setup
        String entitiesTextData = "The Securities and Exchange Commission today announced the leadership of the agency’s trial unit.";
        DocumentRequest<EntitiesOptions> entitiesRequestData = DocumentRequest.<EntitiesOptions>builder()
                .content(entitiesTextData)
                .build();
        RosetteRequest entitiesRequest = this.api.createRosetteRequest(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, entitiesRequestData, EntitiesResponse.class);

        //testing the request
        Future<Response> response = this.api.submitRequest(entitiesRequest);
        assertInstanceOf(EntitiesResponse.class, response.get());
        assertEquals(response.get(), entitiesRequest.getResponse());
    }


    @Test
    void errorResponse() throws ExecutionException, InterruptedException {
        //Api client setup
        this.api = new HttpRosetteAPI.Builder().url(String.format("http://localhost:%d/rest/v1", mockServer.getPort())).build();

        //response setup
        String entitiesResponse = "{   \"code\" : \"badRequestFormat\",   \"message\" : \"no content provided; must be one of an attachment, an inline \\\"content\\\" field, or an external \\\"contentUri\\\"\" }";
        setupResponse("/rest/v1/entities", entitiesResponse, 400, 0, 1);

        //request setup
        DocumentRequest<EntitiesOptions> entitiesRequestData = DocumentRequest.<EntitiesOptions>builder()
                .build();
        RosetteRequest entitiesRequest = this.api.createRosetteRequest(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, entitiesRequestData, EntitiesResponse.class);

        //testing the request
        Future<Response> response = this.api.submitRequest(entitiesRequest);
        assertInstanceOf(ErrorResponse.class, response.get());
        assertEquals(response.get(), entitiesRequest.getResponse());
    }

    @Test
    void testTiming() throws ExecutionException, InterruptedException {
        int delay = 100;
        //api setup
        this.api = new HttpRosetteAPI.Builder().url(String.format("http://localhost:%d/rest/v1", mockServer.getPort()))
                .connectionConcurrency(1).build();

        //responses setup
        int entitiesRespCount = 10;
        int languageRespCount = 4;
        assertEquals(0, entitiesRespCount % 2);
        assertEquals(0, entitiesRespCount % 2);
        String entitiesResponse = "{\"entities\" : [ {     \"type\" : \"ORGANIZATION\",     \"mention\" : \"Securities and Exchange Commission\",     \"normalized\" : \"U.S. Securities and Exchange Commission\",     \"count\" : 1,     \"mentionOffsets\" : [ {       \"startOffset\" : 4,       \"endOffset\" : 38     } ],     \"entityId\" : \"Q953944\",     \"confidence\" : 0.39934742,     \"linkingConfidence\" : 0.67404154   } ] }";
        setupResponse("/rest/v1/entities", entitiesResponse, 200, delay, entitiesRespCount);
        String languageResponse = " {\"code\" : \"badRequestFormat\", \"message\" : \"no content provided; must be one of an attachment, an inline \\\"content\\\" field, or an external \\\"contentUri\\\"\" }";
        setupResponse("/rest/v1/language", languageResponse, 400, delay, languageRespCount);

        //requests setup
        String entitiesTextData = "The Securities and Exchange Commission today announced the leadership of the agency’s trial unit.";
        DocumentRequest<EntitiesOptions> entitiesRequestData = DocumentRequest.<EntitiesOptions>builder()
                .content(entitiesTextData)
                .build();
        DocumentRequest<LanguageOptions> languageRequestData = DocumentRequest.<LanguageOptions>builder().build();
        List<RosetteRequest> requests = new ArrayList<>();
        for (int i = 0; i < entitiesRespCount / 2; i++) {
            requests.add(this.api.createRosetteRequest(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, entitiesRequestData, EntitiesResponse.class));
        }
        for (int i = 0; i < languageRespCount / 2; i++) {
            requests.add(this.api.createRosetteRequest(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, languageRequestData, LanguageResponse.class));
        }

        //run requests
        Date d1 = new Date();
        List<Future<Response>> responses = this.api.submitRequests(requests);
        for (int i = 0; i < responses.size(); i++) {
            responses.get(i).get();
        }
        Date d2 = new Date();

        assertTrue(d2.getTime() - d1.getTime() > delay * requests.size()); // at least as long as the delay in the request

        //run requests concurrency
        int concurrency = 3;
        this.api = new HttpRosetteAPI.Builder().url(String.format("http://localhost:%d/rest/v1", mockServer.getPort()))
                .connectionConcurrency(3).build();

        requests = new ArrayList<>();
        for (int i = 0; i < entitiesRespCount / 2; i++) {
            requests.add(this.api.createRosetteRequest(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, entitiesRequestData, EntitiesResponse.class));
        }
        for (int i = 0; i < entitiesRespCount / 2; i++) {
            requests.add(this.api.createRosetteRequest(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, languageRequestData, LanguageResponse.class));
        }


        d1 = new Date();
        responses = this.api.submitRequests(requests);
        for (int i = 0; i < responses.size(); i++) {
            responses.get(i).get();
        }
        d2 = new Date();

        assertTrue(d2.getTime() - d1.getTime() < delay * requests.size()); // less than serial requests
        assertTrue(d2.getTime() - d1.getTime() > requests.size() / concurrency * delay); // running faster than this would suggest it exceeds the maximum concurrency
    }

    private RosetteRequest setupShutdownTest(int shutdownWaitSeconds, int responseDelayMillis, CloseableHttpClient client) {
        this.api = new HttpRosetteAPI.Builder()
                .url(String.format("http://localhost:%d/rest/v1", mockServer.getPort()))
                .shutdownWait(shutdownWaitSeconds)
                .httpClient(client)
                .build();

        //response setup
        String entitiesResponse = "{\"entities\" : [ {     \"type\" : \"ORGANIZATION\",     \"mention\" : \"Securities and Exchange Commission\",     \"normalized\" : \"U.S. Securities and Exchange Commission\",     \"count\" : 1,     \"mentionOffsets\" : [ {       \"startOffset\" : 4,       \"endOffset\" : 38     } ],     \"entityId\" : \"Q953944\",     \"confidence\" : 0.39934742,     \"linkingConfidence\" : 0.67404154   } ] }";
        setupResponse("/rest/v1/entities", entitiesResponse, 200, responseDelayMillis, 1);

        //request setup
        String entitiesTextData = "The Securities and Exchange Commission today announced the leadership of the agency’s trial unit.";
        DocumentRequest<EntitiesOptions> entitiesRequestData = DocumentRequest.<EntitiesOptions>builder()
                .content(entitiesTextData)
                .build();
        return this.api.createRosetteRequest(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, entitiesRequestData, EntitiesResponse.class);
    }

    @Test
    void successfulShutdown() throws IOException {
        /* creating http client to avoid closing the httpClient of HttpRosetteApi during close()
         *  which automatically closes the pending threads. This way the shutdown behaviour can be tested.*/
        CloseableHttpClient httpClient = HttpClients.createDefault();
        int shutdownWait = 3;
        RosetteRequest request = setupShutdownTest(shutdownWait, 1000, httpClient);
        this.api.submitRequest(request);
        this.api.close();
        httpClient.close();

        assertInstanceOf(EntitiesResponse.class, request.getResponse()); //got response successfully
    }

    @Test
    void timeoutShutdown() throws IOException {
        /* creating http client to avoid closing the httpClient of HttpRosetteApi during close()
         *  which automatically closes the pending threads. This way the shutdown behaviour can be tested.*/
        CloseableHttpClient httpClient = HttpClients.createDefault();
        int shutdownWait = 3;
        RosetteRequest request = setupShutdownTest(shutdownWait, 5000, httpClient);
        this.api.submitRequest(request);
        this.api.close();

        httpClient.close();
        assertNull(request.getResponse()); //didn't get a response, it was forcefully shutdown
    }


    @AfterEach
    void after() throws IOException {
        this.api.close();
    }

}