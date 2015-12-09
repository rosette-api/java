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

import com.basistech.util.LanguageCode;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;

public class InvalidErrorTest extends AbstractTest {

    @Test
    public void notJsonError() throws Exception {
        MockServerClient mockServer = new MockServerClient("localhost", serverPort);
        mockServer.reset()
                .when(HttpRequest.request().withPath(".*/{2,}.*"))
                .respond(HttpResponse.response()
                                .withBody("Invalid path; '//'")
                                .withStatusCode(404)
                );
        String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest//v1";
        RosetteAPI api = new RosetteAPI("my-key-123", mockServiceUrl);
        boolean exceptional = false;
        try {
            api.getCategories("Nothing to see here", LanguageCode.AFRIKAANS, null);
        } catch (RosetteAPIException e) {
            exceptional = true;
            assertEquals("invalidErrorResponse", e.getCode());
            assertEquals(404, e.getHttpStatusCode());
            assertNotNull(e.getMessage());
        }
        assertTrue(exceptional);
    }
}
