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
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityFieldInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityProperties;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRecords;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.apimodel.recordsimilarity.records.AddressField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordType;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordSimilarityRequestTest {

    private static final ObjectMapper MAPPER = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());

    private static final String EXPECTED_JSON = "{\"fields\":{\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true},\"records\":{\"left\":[{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"dob\":\"1993-04-16\",\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"dob\":{\"date\":\"1993-04-16\"}},{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":\"Ivan R\",\"dob\":{\"date\":\"1993-04-16\"},\"addr\":{\"address\":\"123 Roadlane Ave\"}}]}}";
    private static final RecordSimilarityRequest EXPECTED_REQUEST = new RecordSimilarityRequest(null,
            Map.of(
                    "primaryName", new RecordSimilarityFieldInfo(RecordType.NAME, 0.5),
                    "dob", new RecordSimilarityFieldInfo(RecordType.DATE, 0.2),
                    "dob2", new RecordSimilarityFieldInfo(RecordType.DATE, 0.1),
                    "addr", new RecordSimilarityFieldInfo(RecordType.ADDRESS, 0.5)
            ),
            new RecordSimilarityProperties(0.7, true),
            new RecordSimilarityRecords(
                    List.of(
                            Map.of(
                                    "primaryName", new NameField.FieldedName("Ethan R", "PERSON", LanguageCode.ENGLISH, LanguageCode.ENGLISH, ISO15924.Latn),
                                    "dob", new DateField.UnfieldedDate("1993-04-16"),
                                    "dob2", new DateField.FieldedDate("1993/04/16"),
                                    "addr", new AddressField.UnfieldedAddress("123 Roadlane Ave")),
                            Map.of(
                                    "primaryName", new NameField.FieldedName("Evan R", null, null, null, null),
                                    "dob", new DateField.FieldedDate("1993-04-16"))
                    ),
                    List.of(
                            Map.of(
                                    "primaryName", new NameField.FieldedName("Seth R", null, LanguageCode.ENGLISH,  null, null),
                                    "dob", new DateField.FieldedDate("1993-04-16")),
                            Map.of(
                                    "primaryName", new NameField.UnfieldedName("Ivan R"),
                                    "dob", new DateField.FieldedDate("1993-04-16"),
                                    "dob2", new DateField.FieldedDate("1993/04/16"),
                                    "addr", new AddressField.FieldedAddress("123 Roadlane Ave"))
                    )
            )
    );

    @Test
    void testDeserialization() throws JsonProcessingException {
        final RecordSimilarityRequest request = MAPPER.readValue(EXPECTED_JSON, new TypeReference<>() { });
        assertEquals(EXPECTED_REQUEST, request);
    }

    @Test
    void testSerialization() throws JsonProcessingException {
        final JsonNode expectedJson = MAPPER.readTree(EXPECTED_JSON);
        final JsonNode actualJson = MAPPER.valueToTree(EXPECTED_REQUEST);
        assertEquals(expectedJson, actualJson);
    }

}
