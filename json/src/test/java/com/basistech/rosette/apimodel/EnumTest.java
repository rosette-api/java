/*
 * Copyright 2018 Basis Technology Corp.
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

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumTest {
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    public void testCaseInsensitivity() throws Exception {
        String json = "{\"content\": \"foo\", \"options\": {\"modelType\": \"dEfAuLT\"}}";
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<MorphologyOptions>>() { });
        assertTrue(request instanceof DocumentRequest);
        DocumentRequest docRequest = (DocumentRequest) request;
        MorphologyOptions options = (MorphologyOptions) docRequest.getOptions();
        assertEquals(MorphologyModelType.DEFAULT, options.getModelType());
    }
}
