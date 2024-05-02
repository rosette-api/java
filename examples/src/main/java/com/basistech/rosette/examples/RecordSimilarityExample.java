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
package com.basistech.rosette.examples;

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityFieldInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityProperties;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRecords;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResponse;
import com.basistech.rosette.apimodel.recordsimilarity.records.AddressField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordFieldType;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.RECORD_SIMILARITY_SERVICE_PATH;

/**
 * Example which demonstrates record similarity.
 */
@SuppressWarnings({"java:S1166", "java:S2221", "java:S106"})
public class RecordSimilarityExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new RecordSimilarityExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String primaryNameField = "primaryName";
        String dobField = "dob";
        String dob2Field = "dob2";
        String addrField = "addr";
        String dobHyphen = "1993-04-16";
        RecordSimilarityRequest request = RecordSimilarityRequest.builder()
                .fields(Map.of(
                        primaryNameField, RecordSimilarityFieldInfo.builder().type(RecordFieldType.NAME).weight(0.5).build(),
                        dobField, RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.2).build(),
                        dob2Field, RecordSimilarityFieldInfo.builder().type(RecordFieldType.DATE).weight(0.1).build(),
                        addrField, RecordSimilarityFieldInfo.builder().type(RecordFieldType.ADDRESS).weight(0.5).build()))
                .properties(RecordSimilarityProperties.builder().threshold(0.7).includeExplainInfo(true).build())
                .records(RecordSimilarityRecords.builder()
                        .left(
                                List.of(
                                        Map.of(
                                                primaryNameField, NameField.FieldedName.builder()
                                                        .text("Ethan R").entityType("PERSON")
                                                        .language(LanguageCode.ENGLISH)
                                                        .languageOfOrigin(LanguageCode.ENGLISH)
                                                        .script(ISO15924.Latn)
                                                        .build(),
                                                dobField, DateField.UnfieldedDate.builder().date(dobHyphen).build(),
                                                dob2Field, DateField.FieldedDate.builder().date("1993/04/16").build(),
                                                addrField, AddressField.UnfieldedAddress.builder().address("123 Roadlane Ave").build()
                                        ),
                                        Map.of(
                                                primaryNameField, NameField.FieldedName.builder().text("Evan R").build(),
                                                dobField, DateField.FieldedDate.builder().date(dobHyphen).build()
                                        )
                                )
                        ).right(
                                List.of(
                                        Map.of(
                                                primaryNameField, NameField.FieldedName.builder().text("Seth R").language(LanguageCode.ENGLISH).build(),
                                                dobField, DateField.FieldedDate.builder().date(dobHyphen).build()
                                        ),
                                        Map.of(
                                                primaryNameField, NameField.UnfieldedName.builder().text("Ivan R").build(),
                                                dobField, DateField.FieldedDate.builder().date(dobHyphen).build(),
                                                dob2Field, DateField.FieldedDate.builder().date("1993/04/16").build(),
                                                addrField, AddressField.FieldedAddress.builder().houseNumber("123").road("Roadlane Ave").build()
                                        )
                                )
                        ).build()
                ).build();

        HttpRosetteAPI rosetteAPI = new HttpRosetteAPI.Builder()
                                    .key(getApiKeyFromSystemProperty())
                                    .url(getAltUrlFromSystemProperty())
                                    .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        RecordSimilarityResponse response = rosetteAPI.perform(RECORD_SIMILARITY_SERVICE_PATH, request, RecordSimilarityResponse.class);
        System.out.println(responseToJson(response));
    }
}
