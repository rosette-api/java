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

import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.util.LanguageCode;
import com.basistech.util.PartOfSpeechTagSet;
import com.google.common.base.Charsets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Test that is usually ignored, used to debug against a live service.
 */
@org.junit.Ignore
public class DevRosetteAPITest {
    // edit the right URL into place
    private static final String URL = "http://localhost:8181/rest/v1";
    private static final String KEY = null;

    private RosetteAPI api;

    @Before
    public void before() throws Exception {
        api = new RosetteAPI(KEY, URL);
    }

    @After
    public void after() throws IOException {
        api.close();
    }

    @Test
    public void multipart() throws Exception {
        // this assumes that the server has the mock version of the components.
        MorphologyRequest morphologyRequest = new MorphologyRequest.Builder()
                .language(LanguageCode.ENGLISH)
                .options(new MorphologyOptions(false, false, PartOfSpeechTagSet.upt16))
                .contentBytes("This is the cereal shot from 1 gun .".getBytes(Charsets.UTF_8), "text/plain;charset=utf-8")
                .build();
        TokensResponse response = api.doRequest(RosetteAPI.TOKENS_SERVICE_PATH, morphologyRequest, TokensResponse.class);
        assertEquals(9, response.getTokens().size());
        assertEquals("one", response.getTokens().get(6));
    }

    @Test
    public void simple() throws Exception {
        // this assumes that the server has the mock version of the components.
        MorphologyRequest morphologyRequest = new MorphologyRequest.Builder()
                .language(LanguageCode.ENGLISH)
                .options(new MorphologyOptions(false, false, PartOfSpeechTagSet.upt16))
                .content("This is the cereal shot from 1 gun .")
                .build();
        TokensResponse response = api.doRequest(RosetteAPI.TOKENS_SERVICE_PATH, morphologyRequest, TokensResponse.class);
        assertEquals(9, response.getTokens().size());
        assertEquals("one", response.getTokens().get(6));
    }


}
