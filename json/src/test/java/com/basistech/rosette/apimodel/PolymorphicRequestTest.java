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

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PolymorphicRequestTest extends Assert {
    private ObjectMapper mapper;

    @Before
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    public void testRequestTypes() throws Exception {

        String json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"calculateConfidence\": true}}";
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaType\": true, \"calculateConfidence\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        assertNull(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaType\": true, \"calculateConfidence\": true, \"includePermID\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        assertTrue(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"includeDBpediaType\": true, \"calculateConfidence\": true, \"includePermID\": false}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        assertFalse(((DocumentRequest<EntitiesOptions>) request).getOptions().getIncludePermID());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"enableStructuredRegion\": true}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        assertTrue(((DocumentRequest<EntitiesOptions>) request).getOptions().getEnableStructuredRegion());

        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"enableStructuredRegion\": false}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        assertFalse(((DocumentRequest<EntitiesOptions>) request).getOptions().getEnableStructuredRegion());

        json = "{\"name1\": {\"text\": \"Joe\"}, \"name2\": {\"text\": \"Geo\"}}";
        request = mapper.readValue(json, NameSimilarityRequest.class);
        assertTrue(request instanceof NameSimilarityRequest);

        json = "{\"name\": \"Joe\", \"targetLanguage\": \"ara\"}";
        request = mapper.readValue(json, NameTranslationRequest.class);
        assertTrue(request instanceof NameTranslationRequest);

        json = "{\"names\": [{\"text\": \"Joe\"}, {\"text\": \"Smith\"}], \"threshold\": 0.8}";
        request = mapper.readValue(json, NameDeduplicationRequest.class);
        assertTrue(request instanceof NameDeduplicationRequest);

        json = "{\"names\": [\"Joe\", \"Smith\"], \"threshold\": 0.8}";
        request = mapper.readValue(json, NameDeduplicationRequest.class);
        assertTrue(request instanceof NameDeduplicationRequest);

        json = "{\"language\": \"xxx\", \"configuration\": {\"entities\": https://meet.google.com/iah-omkb-egp{ \"LOCATION\": [\"Boston\", \"Mos Eisley\"] } } }";
        request = mapper.readValue(json, new TypeReference<ConfigurationRequest<GazetteerConfiguration>>() { });
        assertTrue(request instanceof ConfigurationRequest);
    }

    @Test
    public void eventsRequests() throws Exception {
        String json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"workspaceId\": \"ws1\"}}";
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<EventsOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        json = "{\"content\": \"what is my type\", \"language\": \"eng\", \"options\": {\"plan\": {\"eng\": [\"abc123\"]}}}";
        request = mapper.readValue(json, new TypeReference<DocumentRequest<EventsOptions>>() { });
        assertTrue(request instanceof DocumentRequest);


    }
}
