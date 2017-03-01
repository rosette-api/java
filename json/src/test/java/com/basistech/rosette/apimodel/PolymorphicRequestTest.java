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
package com.basistech.rosette.apimodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;

public class PolymorphicRequestTest extends Assert {
    private ObjectMapper mapper;

    @Before
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    public void testRequestTypes() throws Exception {
        String json = "{\"content\": \"what is my type\"}";
        Request request = mapper.readValue(json, DocumentRequest.class);
        assertTrue(request instanceof DocumentRequest);

        json = "{\"name1\": {\"text\": \"Joe\"}, \"name2\": {\"text\": \"Geo\"}}";
        request = mapper.readValue(json, NameSimilarityRequest.class);
        assertTrue(request instanceof NameSimilarityRequest);

        json = "{\"name\": \"Joe\", \"targetLanguage\": \"ara\"}";
        request = mapper.readValue(json, NameTranslationRequest.class);
        assertTrue(request instanceof NameTranslationRequest);

        json = "{\"names\": [{\"text\": \"Joe\"}, {\"text\": \"Smith\"}], \"threshold\": 0.8}";
        request = mapper.readValue(json, NameDeduplicationRequest.class);
        assertTrue(request instanceof NameDeduplicationRequest);
    }
}
