/*
 * Copyright 2024 Basis Technology Corp.
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
package com.basistech.rosette.apimodel.recordsimilarity;


import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.apimodel.recordsimilarity.records.AddressField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.NEConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordSimilarityResponseTest {

    private static final ObjectMapper MAPPER = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    private static final String EXPECTED_JSON = "{\"info\":[\"Field threshold not found in properties! Defaulting to 0.0\",\"Field weight not found in fields! Defaulting to 1.0 for all entries\"],\"results\":[{\"explainInfo\":{\"leftOnlyFields\":[\"addr\"],\"scoredFields\":{\"dob\":{\"calculatedWeight\":0.2857142857142857,\"finalScore\":0.74,\"rawScore\":0.8,\"weight\":0.5},\"primaryName\":{\"calculatedWeight\":0.7142857142857143,\"details\":\"any details\",\"finalScore\":0.85,\"rawScore\":0.99,\"weight\":0.5}}},\"left\":{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"primaryName\":{\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\",\"text\":\"Ethan R\"}},\"right\":{\"dob\":\"1993-04-16\",\"primaryName\":{\"text\":\"Seth R\"}},\"score\":0.87},{\"error\":[\"Field foo not found in field mapping\"],\"info\":[\"Some info message\",\"Some other info message\"],\"left\":{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"primaryName\":{\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\",\"text\":\"Ethan R\"}},\"right\":{\"dob\":\"1993-04-16\",\"primaryName\":{\"text\":\"Seth R\"}}}]}";

    private static final RecordSimilarityResponse EXPECTED_RESPONSE;

    static {
        RecordSimilarityResponse temp;
        try {
            temp = RecordSimilarityResponse.builder()
                    .results(List.of(RecordSimilarityResult.builder()
                                    .score(0.87)
                                    .left(Map.of("primaryName", NameField.FieldedName.builder()
                                                    .text("Ethan R")
                                                    .language(LanguageCode.ENGLISH)
                                                    .entityType(NEConstants.toString(NEConstants.NE_TYPE_PERSON))
                                                    .languageOfOrigin(LanguageCode.ENGLISH)
                                                    .script(ISO15924.Latn)
                                                    .build(),
                                            "dob", DateField.FieldedDate.builder()
                                                    .date("1993-04-16")
                                                    .build(),
                                            "addr", AddressField.FieldedAddress.builder()
                                                    .houseNumber("123").road("Roadlane Ave")
                                                    .build()))
                                    .right(Map.of("primaryName", NameField.FieldedName.builder()
                                                    .text("Seth R")
                                                    .build(),
                                            "dob", DateField.UnfieldedDate.builder()
                                                    .date("1993-04-16")
                                                    .build()))
                                    .explainInfo(RecordSimilarityExplainInfo.builder()
                                            .leftOnlyFields(List.of("addr"))
                                            .scoredFields(Map.of("dob", RecordSimilarityFieldExplainInfo.builder()
                                                            .weight(0.5)
                                                            .calculatedWeight(0.2857142857142857)
                                                            .rawScore(0.8)
                                                            .finalScore(0.74)
                                                            .build(),
                                                    "primaryName",
                                                    RecordSimilarityFieldExplainInfo.builder()
                                                            .weight(0.5)
                                                            .calculatedWeight(0.7142857142857143)
                                                            .rawScore(0.99)
                                                            .finalScore(0.85)
                                                            .details(MAPPER.readTree("\"any details\""))
                                                            .build()
                                            ))
                                            .build())
                                    .build(),
                            RecordSimilarityResult.builder()
                                    .left(Map.of("primaryName", NameField.FieldedName.builder()
                                                    .text("Ethan R")
                                                    .language(LanguageCode.ENGLISH)
                                                    .entityType(NEConstants.toString(NEConstants.NE_TYPE_PERSON))
                                                    .languageOfOrigin(LanguageCode.ENGLISH)
                                                    .script(ISO15924.Latn)
                                                    .build(),
                                            "dob", DateField.FieldedDate.builder()
                                                    .date("1993-04-16")
                                                    .build(),
                                            "addr", AddressField.FieldedAddress.builder()
                                                    .houseNumber("123").road("Roadlane Ave")
                                                    .build()))
                                    .right(Map.of("primaryName", NameField.FieldedName.builder()
                                                    .text("Seth R")
                                                    .build(),
                                            "dob", DateField.UnfieldedDate.builder()
                                                    .date("1993-04-16")
                                                    .build()))
                                    .error(Arrays.asList("Field foo not found in field mapping"))
                                    .info(List.of("Some info message", "Some other info message"))
                                    .build()))
                    .info(List.of(
                            "Field threshold not found in properties! Defaulting to 0.0",
                            "Field weight not found in fields! Defaulting to 1.0 for all entries")
                    )
                    .build();
        } catch (JsonProcessingException e) {
            temp = RecordSimilarityResponse.builder().build();
        }
        EXPECTED_RESPONSE = temp;
    }

    @Test
    public void testDeserialization() throws JsonProcessingException {
        // For testing, force ordering
        MAPPER.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        MAPPER.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        final RecordSimilarityResponse response = MAPPER.readValue(EXPECTED_JSON, RecordSimilarityResponse.class);
        //Can't compare response objects directly since fields within names and other RecordSimilarityField may
        // change order, so compare the content of their json strings with fields sorted alphabetically
        assertEquals(MAPPER.writeValueAsString(response), MAPPER.writeValueAsString(EXPECTED_RESPONSE));
    }

    @Test
    public void testSerialization() throws JsonProcessingException {
        // For testing, force ordering
        MAPPER.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        MAPPER.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        assertEquals(EXPECTED_JSON, MAPPER.writeValueAsString(EXPECTED_RESPONSE));
    }
}
