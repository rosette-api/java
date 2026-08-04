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
import com.basistech.rosette.apimodel.recordsimilarity.records.BooleanField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NumberField;
import com.basistech.rosette.apimodel.recordsimilarity.records.StringField;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.NEConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordSimilarityResponseTest {

    private static final ObjectMapper MAPPER = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    private static final String EXPECTED_JSON = "{\"info\":[\"Field threshold not found in properties! Defaulting to 0.0\",\"Field weight not found in fields! Defaulting to 1.0 for all entries\"],\"results\":[{\"explainInfo\":{\"finalScore\":0.0,\"leftOnlyFields\":[\"addr\"],\"rightOnlyFields\":[\"bool\"],\"scoredFields\":{\"dob\":{\"calculatedWeight\":0.2857142857142857,\"finalScore\":0.74,\"rawScore\":0.8,\"weight\":0.33},\"primaryName\":{\"calculatedWeight\":0.7142857142857143,\"details\":\"any details\",\"finalScore\":0.85,\"rawScore\":0.99,\"weight\":0.33},\"str\":{\"calculatedWeight\":0.0,\"finalScore\":0.5,\"rawScore\":0.5,\"weight\":0.33}}},\"left\":{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"num\":2342.15,\"primaryName\":{\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\",\"text\":\"Ethan R\"},\"str\":\"some string\"},\"right\":{\"bool\":false,\"dob\":\"1993-04-16\",\"primaryName\":{\"text\":\"Seth R\"},\"str\":\"some other string\"},\"score\":0.87},{\"error\":[\"Field foo not found in field mapping\"],\"info\":[\"Some info message\",\"Some other info message\"],\"left\":{\"addr\":{\"houseNumber\":\"123\",\"road\":\"Roadlane Ave\"},\"dob\":{\"date\":\"1993-04-16\"},\"primaryName\":{\"entityType\":\"PERSON\",\"language\":\"eng\",\"languageOfOrigin\":\"eng\",\"script\":\"Latn\",\"text\":\"Ethan R\"}},\"right\":{\"dob\":\"1993-04-16\",\"primaryName\":{\"text\":\"Seth R\"}}}]}";

    private static final RecordSimilarityResponse EXPECTED_RESPONSE;

    static {
        RecordSimilarityResponse temp;
        try {
            temp = RecordSimilarityResponse.builder()
                    .results(List.of(RecordSimilarityResult.builder()
                                    .score(0.87)
                                    .left(Map.of(
                                            "primaryName", NameField.builder().data(List.of(
                                                    NameField.FieldedName.builder()
                                                            .text("Ethan R")
                                                            .language(LanguageCode.ENGLISH)
                                                            .entityType(NEConstants.toString(NEConstants.NE_TYPE_PERSON))
                                                            .languageOfOrigin(LanguageCode.ENGLISH)
                                                            .script(ISO15924.Latn)
                                                            .build())).build(),
                                            "dob", DateField.builder().data(List.of(
                                                    DateField.FieldedDate.builder()
                                                            .date("1993-04-16")
                                                            .build())).build(),
                                            "addr", AddressField.builder().data(List.of(
                                                    AddressField.FieldedAddress.builder()
                                                            .houseNumber("123").road("Roadlane Ave")
                                                            .build())).build(),
                                            "str", StringField.builder().data(List.of("some string")).build(),
                                            "num", NumberField.builder().data(List.<Number>of(2342.15)).build()))
                                    .right(Map.of(
                                            "primaryName", NameField.builder().data(List.of(
                                                    NameField.FieldedName.builder()
                                                            .text("Seth R")
                                                            .build())).build(),
                                            "dob", DateField.builder().data(List.of(
                                                    DateField.UnfieldedDate.builder()
                                                            .date("1993-04-16")
                                                            .build())).build(),
                                            "str", StringField.builder().data(List.of("some other string")).build(),
                                            "bool", BooleanField.builder().data(List.of(false)).build()))
                                    .explainInfo(RecordSimilarityExplainInfo.builder()
                                            .leftOnlyFields(List.of("addr"))
                                            .rightOnlyFields(List.of("bool"))
                                            .scoredFields(Map.of("dob", RecordSimilarityFieldExplainInfo.builder()
                                                            .weight(0.33)
                                                            .calculatedWeight(0.2857142857142857)
                                                            .rawScore(0.8)
                                                            .finalScore(0.74)
                                                            .build(),
                                                    "primaryName",
                                                    RecordSimilarityFieldExplainInfo.builder()
                                                            .weight(0.33)
                                                            .calculatedWeight(0.7142857142857143)
                                                            .rawScore(0.99)
                                                            .finalScore(0.85)
                                                            .details(MAPPER.readTree("\"any details\""))
                                                            .build(),
                                                    "str",
                                                    RecordSimilarityFieldExplainInfo.builder()
                                                            .weight(0.33)
                                                            .calculatedWeight(0.0)
                                                            .rawScore(0.5)
                                                            .finalScore(0.5)
                                                            .build()
                                            ))
                                            .build())
                                    .build(),
                            RecordSimilarityResult.builder()
                                    .left(Map.of(
                                            "primaryName", NameField.builder().data(List.of(
                                                    NameField.FieldedName.builder()
                                                            .text("Ethan R")
                                                            .language(LanguageCode.ENGLISH)
                                                            .entityType(NEConstants.toString(NEConstants.NE_TYPE_PERSON))
                                                            .languageOfOrigin(LanguageCode.ENGLISH)
                                                            .script(ISO15924.Latn)
                                                            .build())).build(),
                                            "dob", DateField.builder().data(List.of(
                                                    DateField.FieldedDate.builder()
                                                            .date("1993-04-16")
                                                            .build())).build(),
                                            "addr", AddressField.builder().data(List.of(
                                                    AddressField.FieldedAddress.builder()
                                                            .houseNumber("123").road("Roadlane Ave")
                                                            .build())).build()))
                                    .right(Map.of(
                                            "primaryName", NameField.builder().data(List.of(
                                                    NameField.FieldedName.builder()
                                                            .text("Seth R")
                                                            .build())).build(),
                                            "dob", DateField.builder().data(List.of(
                                                    DateField.UnfieldedDate.builder()
                                                            .date("1993-04-16")
                                                            .build())).build()))
                                    .error(List.of("Field foo not found in field mapping"))
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

    @Test
    public void testMultiElementFieldRoundTrip() throws JsonProcessingException {
        // Use a fresh mapper to avoid polluting the shared MAPPER's serializer cache
        final ObjectMapper mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());

        // Build a response where each field in a result record has multiple values
        final RecordSimilarityResponse response = RecordSimilarityResponse.builder()
                .results(List.of(RecordSimilarityResult.builder()
                        .score(0.75)
                        .left(Map.of(
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
                                        AddressField.FieldedAddress.builder().houseNumber("123").road("Main St").build()
                                )).build(),
                                "str", StringField.builder().data(List.of("engineer", "developer")).build(),
                                "num", NumberField.builder().data(List.<Number>of(1.0, 2.0)).build(),
                                "bool", BooleanField.builder().data(List.of(true, false)).build()))
                        .right(Map.of(
                                "name", NameField.builder().data(List.of(
                                        NameField.FieldedName.builder().text("Ivan R").build()
                                )).build()))
                        .build()))
                .build();

        // Multi-element fields must serialize as JSON arrays
        final com.fasterxml.jackson.databind.JsonNode tree = mapper.valueToTree(response);
        final com.fasterxml.jackson.databind.JsonNode left = tree.get("results").get(0).get("left");
        assertEquals(2, left.get("name").size(), "multi-element name should serialize as array of 2");
        assertEquals(2, left.get("dob").size(), "multi-element dob should serialize as array of 2");
        assertEquals(2, left.get("addr").size(), "multi-element addr should serialize as array of 2");
        assertEquals(2, left.get("str").size(), "multi-element str should serialize as array of 2");
        assertEquals(2, left.get("num").size(), "multi-element num should serialize as array of 2");
        assertEquals(2, left.get("bool").size(), "multi-element bool should serialize as array of 2");

        // First name element is the unfielded string form; second is an object
        assertEquals("Ivan R", left.get("name").get(0).asText());
        assertEquals("Ivan Rossi", left.get("name").get(1).get("text").asText());

        // Round-trip: compare JSON trees (order-insensitive) since deserialized left/right maps
        // hold raw Java objects (RecordSimilarityField is an interface with no type info),
        // not the typed field objects in the original, so object equality cannot be used.
        final String json = mapper.writeValueAsString(response);
        final RecordSimilarityResponse roundTripped = mapper.readValue(json, RecordSimilarityResponse.class);
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(roundTripped)));
    }
}
