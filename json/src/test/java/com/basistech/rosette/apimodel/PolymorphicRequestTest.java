/*
* Copyright 2022 Basis Technology Corp.
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

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolymorphicRequestTest {
    private static final String DOC_REQUEST = DocumentRequest.class.getName();
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    void testRequestTypes() throws Exception {

        String json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"genre\": \"news\"}";
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"calculateConfidence\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaTypes\": true, \"calculateConfidence\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
        assertNull(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaTypes\": true, \"calculateConfidence\": true, \"includePermID\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
        assertTrue(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaTypes\": true, \"calculateConfidence\": true, \"includePermID\": false}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
        assertFalse(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"enableStructuredRegion\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
        assertTrue(((DocumentRequest<EntitiesOptions>) request).getOptions().getEnableStructuredRegion());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"enableStructuredRegion\": false}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
        assertFalse(((DocumentRequest<EntitiesOptions>) request).getOptions().getEnableStructuredRegion());

        json = "{\"name1\": {\"text\": \"Joe\"}, \"name2\": {\"text\": \"Geo\"}}";
        request = mapper.readValue(json, NameSimilarityRequest.class);
        assertEquals(NameSimilarityRequest.class.getName(), request.getClass().getName());

        json = "{\"name\": \"Joe\", \"targetLanguage\": \"ara\"}";
        request = mapper.readValue(json, NameTranslationRequest.class);
        assertEquals(NameTranslationRequest.class.getName(), request.getClass().getName());

        json = "{\"names\": [{\"text\": \"Joe\"}, {\"text\": \"Smith\"}], \"threshold\": 0.8}";
        request = mapper.readValue(json, NameDeduplicationRequest.class);
        assertEquals(NameDeduplicationRequest.class.getName(), request.getClass().getName());

        json = "{\"names\": [\"Joe\", \"Smith\"], \"threshold\": 0.8}";
        request = mapper.readValue(json, NameDeduplicationRequest.class);
        assertEquals(NameDeduplicationRequest.class.getName(), request.getClass().getName());

        json = "{\"language\": \"xxx\", \"configuration\": {\"entities\": { \"LOCATION\": [\"Boston\", \"Mos Eisley\"] } } }";
        request = mapper.readValue(json, new TypeReference<ConfigurationRequest<GazetteerConfiguration>>() { });
        assertEquals(ConfigurationRequest.class.getName(), request.getClass().getName());
    }

    @Test
    void eventsRequests() throws Exception {
        String json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"workspaceId\": \"ws1\"}}";
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<EventsOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"plan\": {\"eng\": [\"abc123\"]}}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EventsOptions>>() { });
        assertEquals(DOC_REQUEST, request.getClass().getName());
    }
}
