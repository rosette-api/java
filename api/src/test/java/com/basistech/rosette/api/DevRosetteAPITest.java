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
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.Request;
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

    private HttpRosetteAPI api;

    @Before
    public void before() throws Exception {
        api = new HttpRosetteAPI.Builder().key(KEY).url(URL).build();
    }

    @After
    public void after() throws IOException {
        api.close();
    }

    @Test
    public void multipart() throws Exception {
        // this assumes that the server has the mock version of the components.
        Request morphologyRequest = DocumentRequest.builder().language(LanguageCode.ENGLISH)
                .options(MorphologyOptions.builder().partOfSpeechTagSet(PartOfSpeechTagSet.upt16).build())
                .content("This is the cereal shot from 1 gun .".getBytes(Charsets.UTF_8), "text/plain;charset=utf-8")
                .build();
        TokensResponse response = api.perform(AbstractRosetteAPI.TOKENS_SERVICE_PATH, morphologyRequest, TokensResponse.class);
        assertEquals(9, response.getTokens().size());
        assertEquals("one", response.getTokens().get(6));
    }

    @Test
    public void simple() throws Exception {
        // this assumes that the server has the mock version of the components.
        Request morphologyRequest = DocumentRequest.builder().language(LanguageCode.ENGLISH)
                .options(MorphologyOptions.builder().partOfSpeechTagSet(PartOfSpeechTagSet.upt16).build())
                .content("This is the cereal shot from 1 gun .")
                .build();
        TokensResponse response = api.perform(AbstractRosetteAPI.TOKENS_SERVICE_PATH, morphologyRequest, TokensResponse.class);
        assertEquals(9, response.getTokens().size());
        assertEquals("one", response.getTokens().get(6));
    }
}
