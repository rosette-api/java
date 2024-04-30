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
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordFieldType;
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

    private static final String EXPECTED_JSON = "{\"fields\":{\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true},\"records\":{\"left\":[{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"dob\":\"1993-04-16\",\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"dob\":{\"date\":\"1993-04-16\"}},{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":\"Ivan R\",\"dob\":{\"date\":\"1993-04-16\"},\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"}}]}}";
    private static final String EXPECTED_JSON_WITH_PARAMS = "{\"fields\":{\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true,\"parameters\":{\"timeDistanceWeight\":\"0.8\",\"stringDistanceWeight\":\"0.1\"}},\"records\":{\"left\":[{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"dob\":\"1993-04-16\",\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"dob\":{\"date\":\"1993-04-16\"}},{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":\"Ivan R\",\"dob\":{\"date\":\"1993-04-16\"},\"addr\":{\"address\":\"123 Roadlane Ave\"}}]}}";
    private static final String EXPECTED_JSON_WITH_UNIVERSE = "{\"fields\":{\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true,\"parameterUniverse\":\"myParameterUniverse\"},\"records\":{\"left\":[{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"dob\":\"1993-04-16\",\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"dob\":{\"date\":\"1993-04-16\"}},{\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":\"Ivan R\",\"dob\":{\"date\":\"1993-04-16\"},\"addr\":{\"address\":\"123 Roadlane Ave\"}}]}}";
    private static final RecordSimilarityRequest EXPECTED_REQUEST = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.ADDRESS).weight(0.5).build(),
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.1).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.NAME).weight(0.5).build(),
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.2).build()))
            .properties(RecordSimilarityProperties.builder().threshold(0.7).includeExplainInfo(true).build())
            .records(RecordSimilarityRecords.builder()
                .left(
                    List.of(
                        Map.of(
                            "primaryName", NameField.FieldedName.builder()
                                    .text("Ethan R").entityType("PERSON")
                                    .language(LanguageCode.ENGLISH)
                                    .languageOfOrigin(LanguageCode.ENGLISH)
                                    .script(ISO15924.Latn)
                                    .build(),
                            "dob", DateField.UnfieldedDate.builder().date("1993-04-16").build(),
                            "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                            "addr", AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build()
                        ),
                        Map.of(
                            "primaryName", NameField.FieldedName.builder().text("Evan R").build(),
                            "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                        )
                    )
                ).right(
                    List.of(
                        Map.of(
                                "primaryName", NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build(),
                                "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                        ),
                        Map.of(
                                "primaryName", NameField.UnfieldedName.builder().text("Ivan R").build(),
                                "dob", DateField.FieldedDate.builder().date("1993-04-16").build(),
                                "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                                "addr", AddressField.FieldedAddress.builder().address("123 Roadlane Ave").build()
                        )
                    )
                ).build()
            ).build();

    private static final RecordSimilarityRequest EXPECTED_REQUEST_WITH_PARAMS = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.1).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.NAME).weight(0.5).build(),
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.2).build(),
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.ADDRESS).weight(0.5).build()))
            .properties(RecordSimilarityProperties.builder()
                    .threshold(0.7)
                    .includeExplainInfo(true)
                    .parameters(
                            Map.of(
                                    "timeDistanceWeight", "0.8",
                                    "stringDistanceWeight", "0.1"
                            )
                    )
                    .build())
            .records(RecordSimilarityRecords.builder()
                    .left(
                            List.of(
                                    Map.of(
                                            "primaryName", NameField.FieldedName.builder()
                                                    .text("Ethan R").entityType("PERSON")
                                                    .language(LanguageCode.ENGLISH)
                                                    .languageOfOrigin(LanguageCode.ENGLISH)
                                                    .script(ISO15924.Latn)
                                                    .build(),
                                            "dob", DateField.UnfieldedDate.builder().date("1993-04-16").build(),
                                            "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                                            "addr", AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build()
                                    ),
                                    Map.of(
                                            "primaryName", NameField.FieldedName.builder().text("Evan R").build(),
                                            "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                                    )
                            )
                    ).right(
                            List.of(
                                    Map.of(
                                            "primaryName", NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build(),
                                            "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                                    ),
                                    Map.of(
                                            "primaryName", NameField.UnfieldedName.builder().text("Ivan R").build(),
                                            "dob", DateField.FieldedDate.builder().date("1993-04-16").build(),
                                            "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                                            "addr", AddressField.FieldedAddress.builder().address("123 Roadlane Ave").build()
                                    )
                            )
                    ).build()
            ).build();

    private static final RecordSimilarityRequest EXPECTED_REQUEST_WITH_UNIVERSE = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.2).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.NAME).weight(0.5).build(),
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.1).build(),
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.ADDRESS).weight(0.5).build()))
            .properties(RecordSimilarityProperties.builder()
                    .threshold(0.7)
                    .includeExplainInfo(true)
                    .parameterUniverse("myParameterUniverse")
                    .build())
            .records(RecordSimilarityRecords.builder()
                .left(
                    List.of(
                        Map.of(
                            "primaryName", NameField.FieldedName.builder()
                                    .text("Ethan R").entityType("PERSON")
                                    .language(LanguageCode.ENGLISH)
                                    .languageOfOrigin(LanguageCode.ENGLISH)
                                    .script(ISO15924.Latn)
                                    .build(),
                            "dob", DateField.UnfieldedDate.builder().date("1993-04-16").build(),
                            "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                            "addr", AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build()
                        ),
                        Map.of(
                            "primaryName", NameField.FieldedName.builder().text("Evan R").build(),
                            "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                        )
                    )
                ).right(
                    List.of(
                        Map.of(
                                "primaryName", NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build(),
                                "dob", DateField.FieldedDate.builder().date("1993-04-16").build()
                        ),
                        Map.of(
                                "primaryName", NameField.UnfieldedName.builder().text("Ivan R").build(),
                                "dob", DateField.FieldedDate.builder().date("1993-04-16").build(),
                                "dob2", DateField.FieldedDate.builder().date("1993/04/16").build(),
                                "addr", AddressField.FieldedAddress.builder().houseNumber("123").road("Roadlane Ave").build()
                        )
                    )
                ).build()
            ).build();

    @Test
    void testDeserialization() throws JsonProcessingException {
        final RecordSimilarityRequest request = MAPPER.readValue(EXPECTED_JSON, new TypeReference<>() { });
        assertEquals(EXPECTED_REQUEST, request);

        final RecordSimilarityRequest requestParams = MAPPER.readValue(EXPECTED_JSON_WITH_PARAMS, new TypeReference<>() { });
        assertEquals(EXPECTED_REQUEST_WITH_PARAMS, requestParams);

        final RecordSimilarityRequest requestUniverse = MAPPER.readValue(EXPECTED_JSON_WITH_UNIVERSE, new TypeReference<>() { });
        assertEquals(EXPECTED_REQUEST_WITH_UNIVERSE, requestUniverse);
    }

    @Test
    void testSerialization() throws JsonProcessingException {
        final JsonNode expectedJson = MAPPER.readTree(EXPECTED_JSON);
        final JsonNode actualJson = MAPPER.valueToTree(EXPECTED_REQUEST);
        assertEquals(expectedJson, actualJson);

        final JsonNode expectedParamJson = MAPPER.readTree(EXPECTED_JSON_WITH_PARAMS);
        final JsonNode actualParamJson = MAPPER.valueToTree(EXPECTED_REQUEST_WITH_PARAMS);
        assertEquals(expectedParamJson, actualParamJson);

        final JsonNode expectedUniverseJson = MAPPER.readTree(EXPECTED_JSON_WITH_UNIVERSE);
        final JsonNode actualUniverseJson = MAPPER.valueToTree(EXPECTED_REQUEST_WITH_UNIVERSE);
        assertEquals(expectedUniverseJson, actualUniverseJson);
    }

}
