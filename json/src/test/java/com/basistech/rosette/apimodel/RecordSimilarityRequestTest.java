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
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityField;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityProperties;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRecords;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.apimodel.recordsimilarity.records.AddressRecord;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateRecord;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameRecord;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordType;
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

    private static final String EXPECTED_JSON = "{\"fields\":{\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},\"properties\":{\"threshold\":0.7},\"records\":{\"left\":[{\"primaryName\":{\"text\":\"Ethan R\",\"language\":\"eng\",\"entityType\":\"PERSON\"},\"dob\":\"1993-04-16\",\"dob2\":{\"date\":\"1993/04/16\"},\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\"},\"dob\":{\"date\":\"1993-04-16\"}},{\"primaryName\":\"Ivan R\",\"dob\":{\"date\":\"1993-04-16\"},\"dob2\":{\"date\":\"1993/04/16\"},\"addr\":{\"address\":\"123 Roadlane Ave\"}}]}}";
    private static final RecordSimilarityRequest EXPECTED_REQUEST = new RecordSimilarityRequest(
            Map.of(
                    "primaryName", new RecordSimilarityField(RecordType.NAME, 0.5),
                    "dob", new RecordSimilarityField(RecordType.DATE, 0.2),
                    "dob2", new RecordSimilarityField(RecordType.DATE, 0.1),
                    "addr", new RecordSimilarityField(RecordType.ADDRESS, 0.5)
            ),
            new RecordSimilarityProperties(0.7),
            new RecordSimilarityRecords(
                    List.of(
                            Map.of(
                                    "primaryName", new NameRecord.FieldedNameRecord("Ethan R", "PERSON", LanguageCode.ENGLISH),
                                    "dob", new DateRecord.UnfieldedDateRecord("1993-04-16"),
                                    "dob2", new DateRecord.FieldedDateRecord("1993/04/16"),
                                    "addr", new AddressRecord.UnfieldedAddressRecord("123 Roadlane Ave")),
                            Map.of(
                                    "primaryName", new NameRecord.FieldedNameRecord("Evan R", null, null),
                                    "dob", new DateRecord.FieldedDateRecord("1993-04-16"))
                    ),
                    List.of(
                            Map.of(
                                    "primaryName", new NameRecord.FieldedNameRecord("Seth R", null, null),
                                    "dob", new DateRecord.FieldedDateRecord("1993-04-16")),
                            Map.of(
                                    "primaryName", new NameRecord.UnfieldedNameRecord("Ivan R"),
                                    "dob", new DateRecord.FieldedDateRecord("1993-04-16"),
                                    "dob2", new DateRecord.FieldedDateRecord("1993/04/16"),
                                    "addr", new AddressRecord.FieldedAddressRecord("123 Roadlane Ave"))
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
