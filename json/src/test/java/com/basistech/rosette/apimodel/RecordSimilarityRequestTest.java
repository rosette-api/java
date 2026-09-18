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
import com.basistech.rosette.apimodel.recordsimilarity.records.BooleanField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NumberField;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordFieldType;
import com.basistech.rosette.apimodel.recordsimilarity.records.StringField;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecordSimilarityRequestTest {

    private static final ObjectMapper MAPPER = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());

    private static final String EXPECTED_JSON = "{\"fields\":{\"num\":{\"type\":\"rni_number\",\"weight\":0.25},\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"bool\":{\"type\":\"rni_boolean\",\"weight\":0.05},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5,\"scoreIfNull\":0.8},\"str\":{\"type\":\"rni_string\",\"weight\":0.8},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true},\"records\":{\"left\":[{\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"num\":42.0,\"dob\":\"1993-04-16\",\"dob2\":{\"date\":\"1993/04/16\",\"format\":\"yyyy/MM/dd\"},\"addr\":\"123 Roadlane Ave\"},{\"primaryName\":{\"text\":\"Evan R\"},\"dob\":{\"date\":\"1993-04-16\"},\"str\":\"some string\",\"bool\":false}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"num\":74301945813095,\"dob\":{\"date\":\"1993-04-16\"},\"bool\":true},{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"str\":\"some other string\",\"dob2\":{\"date\":\"1993/04/16\"},\"primaryName\":\"Ivan R\",\"bool\":true,\"dob\":{\"date\":\"1993-04-16\"}}]}}";
    private static final String EXPECTED_JSON_WITH_PARAMS = "{\"fields\":{\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"str\":{\"type\":\"rni_string\",\"weight\":0.8},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5},\"bool\":{\"type\":\"rni_boolean\",\"weight\":0.05},\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1},\"num\":{\"type\":\"rni_number\",\"weight\":0.25}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true,\"parameters\":{\"stringDistanceWeight\":\"0.1\",\"timeDistanceWeight\":\"0.8\"}},\"records\":{\"left\":[{\"addr\":\"123 Roadlane Ave\",\"dob2\":{\"date\":\"1993/04/16\"},\"dob\":\"1993-04-16\",\"num\":42.0,\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"}},{\"dob\":{\"date\":\"1993-04-16\"},\"primaryName\":{\"text\":\"Evan R\"},\"bool\":false,\"str\":\"some string\"}],\"right\":[{\"dob\":{\"date\":\"1993-04-16\"},\"num\":74301945813095,\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"bool\":true},{\"primaryName\":\"Ivan R\",\"dob2\":{\"date\":\"1993/04/16\"},\"str\":\"some other string\",\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"bool\":true}]}}";
    private static final String EXPECTED_JSON_WITH_UNIVERSE = "{\"fields\":{\"num\":{\"type\":\"rni_number\",\"weight\":0.25},\"primaryName\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"str\":{\"type\":\"rni_string\",\"weight\":0.8},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5},\"bool\":{\"type\":\"rni_boolean\",\"weight\":0.05},\"dob2\":{\"type\":\"rni_date\",\"weight\":0.1}},\"properties\":{\"threshold\":0.7,\"includeExplainInfo\":true,\"parameterUniverse\":\"myParameterUniverse\"},\"records\":{\"left\":[{\"num\":42.0,\"primaryName\":{\"text\":\"Ethan R\",\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\"},\"addr\":\"123 Roadlane Ave\",\"dob2\":{\"date\":\"1993/04/16\"},\"dob\":\"1993-04-16\"},{\"primaryName\":{\"text\":\"Evan R\"},\"bool\":false,\"str\":\"some string\",\"dob\":{\"date\":\"1993-04-16\"}}],\"right\":[{\"primaryName\":{\"text\":\"Seth R\",\"language\":\"eng\"},\"bool\":true,\"dob\":{\"date\":\"1993-04-16\"},\"num\":74301945813095},{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"bool\":true,\"primaryName\":\"Ivan R\",\"dob2\":{\"date\":\"1993/04/16\"},\"str\":\"some other string\"}]}}";

    // Old single-value JSON format — deserializers must still accept this as single-element lists.
    private static final String BACKWARD_COMPAT_JSON = "{\"fields\":{\"name\":{\"type\":\"rni_name\",\"weight\":0.5},\"dob\":{\"type\":\"rni_date\",\"weight\":0.2},\"addr\":{\"type\":\"rni_address\",\"weight\":0.5},\"str\":{\"type\":\"rni_string\",\"weight\":0.8},\"num\":{\"type\":\"rni_number\",\"weight\":0.25},\"bool\":{\"type\":\"rni_boolean\",\"weight\":0.05}},\"properties\":{\"threshold\":0.7},\"records\":{\"left\":[{\"name\":\"Ivan R\",\"dob\":\"1993-04-16\",\"addr\":\"123 Main St\",\"str\":\"engineer\",\"num\":42.0,\"bool\":true}],\"right\":[{\"name\":{\"text\":\"Ivan R\"},\"dob\":{\"date\":\"1993-04-16\"},\"addr\":{\"houseNumber\":\"123\",\"road\":\"Main St\"},\"str\":\"engineer\",\"num\":42.0,\"bool\":true}]}}";

    private static final RecordSimilarityRequest EXPECTED_REQUEST = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_ADDRESS).weight(0.5).scoreIfNull(0.8).build(),
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.1).scoreIfNull(null).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NAME).weight(0.5).build(),
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.2).build(),
                    "str", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_STRING).weight(0.8).build(),
                    "num", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NUMBER).weight(0.25).build(),
                    "bool", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_BOOLEAN).weight(0.05).build()))
            .properties(RecordSimilarityProperties.builder().threshold(0.7).includeExplainInfo(true).build())
            .records(RecordSimilarityRecords.builder()
                .left(List.of(
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder()
                                        .text("Ethan R").entityType("PERSON")
                                        .language(LanguageCode.ENGLISH)
                                        .languageOfOrigin(LanguageCode.ENGLISH)
                                        .script(ISO15924.Latn)
                                        .build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.UnfieldedDate.builder().date("1993-04-16").build())).build(),
                        "dob2", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993/04/16").format("yyyy/MM/dd").build())).build(),
                        "addr", AddressField.builder().data(List.of(
                                AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build())).build(),
                        "num", NumberField.builder().data(List.<Number>of(42.0)).build()
                    ),
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder().text("Evan R").build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "str", StringField.builder().data(List.of("some string")).build(),
                        "bool", BooleanField.builder().data(List.of(false)).build()
                    )
                ))
                .right(List.of(
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "num", NumberField.builder().data(List.<Number>of(74301945813095L)).build(),
                        "bool", BooleanField.builder().data(List.of(true)).build()
                    ),
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.UnfieldedName.builder().text("Ivan R").build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "dob2", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993/04/16").build())).build(),
                        "addr", AddressField.builder().data(List.of(
                                AddressField.FieldedAddress.builder().houseNumber("123").road("Roadlane Ave").build())).build(),
                        "str", StringField.builder().data(List.of("some other string")).build(),
                        "bool", BooleanField.builder().data(List.of(true)).build()
                    )
                ))
                .build()
            ).build();

    private static final RecordSimilarityRequest EXPECTED_REQUEST_WITH_PARAMS = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.1).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NAME).weight(0.5).build(),
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.2).build(),
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_ADDRESS).weight(0.5).build(),
                    "str", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_STRING).weight(0.8).build(),
                    "num", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NUMBER).weight(0.25).build(),
                    "bool", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_BOOLEAN).weight(0.05).build()))
            .properties(RecordSimilarityProperties.builder()
                    .threshold(0.7)
                    .includeExplainInfo(true)
                    .parameters(Map.of(
                            "timeDistanceWeight", "0.8",
                            "stringDistanceWeight", "0.1"))
                    .build())
            .records(RecordSimilarityRecords.builder()
                    .left(List.of(
                        Map.of(
                            "primaryName", NameField.builder().data(List.of(
                                    NameField.FieldedName.builder()
                                            .text("Ethan R").entityType("PERSON")
                                            .language(LanguageCode.ENGLISH)
                                            .languageOfOrigin(LanguageCode.ENGLISH)
                                            .script(ISO15924.Latn)
                                            .build())).build(),
                            "dob", DateField.builder().data(List.of(
                                    DateField.UnfieldedDate.builder().date("1993-04-16").build())).build(),
                            "dob2", DateField.builder().data(List.of(
                                    DateField.FieldedDate.builder().date("1993/04/16").build())).build(),
                            "addr", AddressField.builder().data(List.of(
                                    AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build())).build(),
                            "num", NumberField.builder().data(List.<Number>of(42.0)).build()
                        ),
                        Map.of(
                            "primaryName", NameField.builder().data(List.of(
                                    NameField.FieldedName.builder().text("Evan R").build())).build(),
                            "dob", DateField.builder().data(List.of(
                                    DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                            "str", StringField.builder().data(List.of("some string")).build(),
                            "bool", BooleanField.builder().data(List.of(false)).build()
                        )
                    ))
                    .right(List.of(
                        Map.of(
                            "primaryName", NameField.builder().data(List.of(
                                    NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build())).build(),
                            "dob", DateField.builder().data(List.of(
                                    DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                            "num", NumberField.builder().data(List.<Number>of(74301945813095L)).build(),
                            "bool", BooleanField.builder().data(List.of(true)).build()
                        ),
                        Map.of(
                            "primaryName", NameField.builder().data(List.of(
                                    NameField.UnfieldedName.builder().text("Ivan R").build())).build(),
                            "dob", DateField.builder().data(List.of(
                                    DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                            "dob2", DateField.builder().data(List.of(
                                    DateField.FieldedDate.builder().date("1993/04/16").build())).build(),
                            "addr", AddressField.builder().data(List.of(
                                    AddressField.FieldedAddress.builder().houseNumber("123").road("Roadlane Ave").build())).build(),
                            "str", StringField.builder().data(List.of("some other string")).build(),
                            "bool", BooleanField.builder().data(List.of(true)).build()
                        )
                    ))
                    .build()
            ).build();

    private static final RecordSimilarityRequest EXPECTED_REQUEST_WITH_UNIVERSE = RecordSimilarityRequest.builder()
            .fields(Map.of(
                    "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.2).build(),
                    "primaryName", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NAME).weight(0.5).build(),
                    "dob2", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.1).build(),
                    "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_ADDRESS).weight(0.5).build(),
                    "str", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_STRING).weight(0.8).build(),
                    "num", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NUMBER).weight(0.25).build(),
                    "bool", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_BOOLEAN).weight(0.05).build()))
            .properties(RecordSimilarityProperties.builder()
                    .threshold(0.7)
                    .includeExplainInfo(true)
                    .parameterUniverse("myParameterUniverse")
                    .build())
            .records(RecordSimilarityRecords.builder()
                .left(List.of(
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder()
                                        .text("Ethan R").entityType("PERSON")
                                        .language(LanguageCode.ENGLISH)
                                        .languageOfOrigin(LanguageCode.ENGLISH)
                                        .script(ISO15924.Latn)
                                        .build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.UnfieldedDate.builder().date("1993-04-16").build())).build(),
                        "dob2", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993/04/16").build())).build(),
                        "addr", AddressField.builder().data(List.of(
                                AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build())).build(),
                        "num", NumberField.builder().data(List.<Number>of(42.0)).build()
                    ),
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder().text("Evan R").build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "str", StringField.builder().data(List.of("some string")).build(),
                        "bool", BooleanField.builder().data(List.of(false)).build()
                    )
                ))
                .right(List.of(
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "num", NumberField.builder().data(List.<Number>of(74301945813095L)).build(),
                        "bool", BooleanField.builder().data(List.of(true)).build()
                    ),
                    Map.of(
                        "primaryName", NameField.builder().data(List.of(
                                NameField.UnfieldedName.builder().text("Ivan R").build())).build(),
                        "dob", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                        "dob2", DateField.builder().data(List.of(
                                DateField.FieldedDate.builder().date("1993/04/16").build())).build(),
                        "addr", AddressField.builder().data(List.of(
                                AddressField.FieldedAddress.builder().houseNumber("123").road("Roadlane Ave").build())).build(),
                        "str", StringField.builder().data(List.of("some other string")).build(),
                        "bool", BooleanField.builder().data(List.of(true)).build()
                    )
                ))
                .build()
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

    @Test
    void testSingleValueDeserializationBackwardCompat() throws JsonProcessingException {
        // Old-format JSON uses bare single values instead of arrays; deserializers must produce single-element lists.
        final RecordSimilarityRequest actual = MAPPER.readValue(BACKWARD_COMPAT_JSON, new TypeReference<>() { });

        final RecordSimilarityRequest expected = RecordSimilarityRequest.builder()
                .fields(Map.of(
                        "name", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NAME).weight(0.5).build(),
                        "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.2).build(),
                        "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_ADDRESS).weight(0.5).build(),
                        "str", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_STRING).weight(0.8).build(),
                        "num", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NUMBER).weight(0.25).build(),
                        "bool", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_BOOLEAN).weight(0.05).build()))
                .properties(RecordSimilarityProperties.builder().threshold(0.7).build())
                .records(RecordSimilarityRecords.builder()
                        .left(List.of(Map.of(
                                "name", NameField.builder().data(List.of(
                                        NameField.UnfieldedName.builder().text("Ivan R").build())).build(),
                                "dob", DateField.builder().data(List.of(
                                        DateField.UnfieldedDate.builder().date("1993-04-16").build())).build(),
                                "addr", AddressField.builder().data(List.of(
                                        AddressField.UnfieldedAddress.builder().address("123 Main St").build())).build(),
                                "str", StringField.builder().data(List.of("engineer")).build(),
                                "num", NumberField.builder().data(List.<Number>of(42.0)).build(),
                                "bool", BooleanField.builder().data(List.of(true)).build()
                        )))
                        .right(List.of(Map.of(
                                "name", NameField.builder().data(List.of(
                                        NameField.FieldedName.builder().text("Ivan R").build())).build(),
                                "dob", DateField.builder().data(List.of(
                                        DateField.FieldedDate.builder().date("1993-04-16").build())).build(),
                                "addr", AddressField.builder().data(List.of(
                                        AddressField.FieldedAddress.builder().houseNumber("123").road("Main St").build())).build(),
                                "str", StringField.builder().data(List.of("engineer")).build(),
                                "num", NumberField.builder().data(List.<Number>of(42.0)).build(),
                                "bool", BooleanField.builder().data(List.of(true)).build()
                        )))
                        .build())
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void testMultiElementFieldRoundTrip() throws JsonProcessingException {
        // Use a fresh mapper to avoid polluting the shared MAPPER's serializer cache
        final ObjectMapper mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());

        // A record with multiple values in every field type
        final RecordSimilarityRequest request = RecordSimilarityRequest.builder()
                .fields(Map.of(
                        "name", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NAME).weight(0.5).build(),
                        "dob", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_DATE).weight(0.2).build(),
                        "addr", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_ADDRESS).weight(0.5).build(),
                        "str", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_STRING).weight(0.8).build(),
                        "num", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_NUMBER).weight(0.25).build(),
                        "bool", RecordSimilarityFieldInfo.builder().type(RecordFieldType.RNI_BOOLEAN).weight(0.05).build()))
                .properties(RecordSimilarityProperties.builder().threshold(0.7).build())
                .records(RecordSimilarityRecords.builder()
                        .left(List.of(Map.of(
                                "name", NameField.builder().data(List.of(
                                        NameField.UnfieldedName.builder().text("Ivan R").build(),
                                        NameField.FieldedName.builder().text("Ivan Rossi").language(LanguageCode.ITALIAN).build()
                                )).build(),
                                "dob", DateField.builder().data(List.of(
                                        DateField.UnfieldedDate.builder().date("1993-04-16").build(),
                                        DateField.FieldedDate.builder().date("1993/04/16").format("yyyy/MM/dd").build()
                                )).build(),
                                "addr", AddressField.builder().data(List.of(
                                        AddressField.UnfieldedAddress.builder().address("123 Main St").build(),
                                        AddressField.FieldedAddress.builder().houseNumber("123").road("Main St").city("Springfield").build()
                                )).build(),
                                "str", StringField.builder().data(List.of("engineer", "developer")).build(),
                                "num", NumberField.builder().data(List.<Number>of(42.0, 43.5)).build(),
                                "bool", BooleanField.builder().data(List.of(true, false)).build()
                        )))
                        .right(List.of(Map.of(
                                "name", NameField.builder().data(List.of(
                                        NameField.FieldedName.builder().text("Ivan R").build(),
                                        NameField.UnfieldedName.builder().text("I. Rossi").build()
                                )).build(),
                                "dob", DateField.builder().data(List.of(
                                        DateField.UnfieldedDate.builder().date("1993-04-16").build()
                                )).build(),
                                "addr", AddressField.builder().data(List.of(
                                        AddressField.FieldedAddress.builder().houseNumber("123").road("Main St").build()
                                )).build(),
                                "str", StringField.builder().data(List.of("software engineer")).build(),
                                "num", NumberField.builder().data(List.<Number>of(42.0)).build(),
                                "bool", BooleanField.builder().data(List.of(true)).build()
                        )))
                        .build())
                .build();

        // Multi-element fields must serialize as JSON arrays
        final JsonNode tree = mapper.valueToTree(request);
        final JsonNode leftRecord = tree.get("records").get("left").get(0);
        assertEquals(2, leftRecord.get("name").size(), "multi-element name should be an array of 2");
        assertEquals(2, leftRecord.get("dob").size(), "multi-element dob should be an array of 2");
        assertEquals(2, leftRecord.get("addr").size(), "multi-element addr should be an array of 2");
        assertEquals(2, leftRecord.get("str").size(), "multi-element str should be an array of 2");
        assertEquals(2, leftRecord.get("num").size(), "multi-element num should be an array of 2");
        assertEquals(2, leftRecord.get("bool").size(), "multi-element bool should be an array of 2");

        // Mixed unfielded/fielded name elements
        assertEquals("Ivan R", leftRecord.get("name").get(0).asText());
        assertEquals("Ivan Rossi", leftRecord.get("name").get(1).get("text").asText());

        // Round-trip: deserialize back and compare
        final String json = mapper.writeValueAsString(request);
        final RecordSimilarityRequest roundTripped = mapper.readValue(json, new TypeReference<>() { });
        assertEquals(request, roundTripped);
    }

    @Test
    void testNullDataThrowsOnConstruction() {
        assertThrows(NullPointerException.class, () -> NameField.builder().build(),
                "NameField.builder().build() with null data should throw NPE");
        assertThrows(NullPointerException.class, () -> DateField.builder().build(),
                "DateField.builder().build() with null data should throw NPE");
        assertThrows(NullPointerException.class, () -> AddressField.builder().build(),
                "AddressField.builder().build() with null data should throw NPE");
        assertThrows(NullPointerException.class, () -> StringField.builder().build(),
                "StringField.builder().build() with null data should throw NPE");
        assertThrows(NullPointerException.class, () -> NumberField.builder().build(),
                "NumberField.builder().build() with null data should throw NPE");
        assertThrows(NullPointerException.class, () -> BooleanField.builder().build(),
                "BooleanField.builder().build() with null data should throw NPE");
    }

    @Test
    void testMalformedArrayElementThrows() {
        // A number where a name element is expected is invalid JSON for NameField
        final String badNameJson = "{\"fields\":{\"name\":{\"type\":\"rni_name\",\"weight\":0.5}},"
                + "\"records\":{\"left\":[{\"name\":[42]}],\"right\":[]}}";
        assertThrows(JsonProcessingException.class,
                () -> MAPPER.readValue(badNameJson, new TypeReference<RecordSimilarityRequest>() { }),
                "Numeric element inside name array should fail deserialization");

        // A number where a date element is expected
        final String badDateJson = "{\"fields\":{\"dob\":{\"type\":\"rni_date\",\"weight\":0.2}},"
                + "\"records\":{\"left\":[{\"dob\":[42]}],\"right\":[]}}";
        assertThrows(JsonProcessingException.class,
                () -> MAPPER.readValue(badDateJson, new TypeReference<RecordSimilarityRequest>() { }),
                "Numeric element inside date array should fail deserialization");

        // A number where an address element is expected
        final String badAddrJson = "{\"fields\":{\"addr\":{\"type\":\"rni_address\",\"weight\":0.5}},"
                + "\"records\":{\"left\":[{\"addr\":[42]}],\"right\":[]}}";
        assertThrows(JsonProcessingException.class,
                () -> MAPPER.readValue(badAddrJson, new TypeReference<RecordSimilarityRequest>() { }),
                "Numeric element inside address array should fail deserialization");
    }

}
