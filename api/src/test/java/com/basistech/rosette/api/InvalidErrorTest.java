/******************************************************************************
 * * This data and information is proprietary to, and a valuable trade secret
 * * of, Basis Technology Corp.  It is given in confidence by Basis Technology
 * * and may only be used as permitted under the license agreement under which
 * * it has been distributed, and in no other way.
 * *
 * * Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 * *
 * * The technical data and information provided herein are provided with
 * * `limited rights', and the computer software provided herein is provided
 * * with `restricted rights' as those terms are defined in DAR and ASPR
 * * 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.LanguageCode;
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
            api.getCategories("Nothing to see here", LanguageCode.afr, null);
        } catch (RosetteAPIException e) {
            exceptional = true;
            assertEquals("invalidErrorResponse", e.getCode());
            assertEquals(404, e.getHttpStatusCode());
            assertNotNull(e.getMessage());
        }
        assertTrue(exceptional);
    }
}
