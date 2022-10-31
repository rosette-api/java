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
package com.basistech.rosette.examples;

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.util.LanguageCode;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.NAME_TRANSLATION_SERVICE_PATH;

/**
 * Example which demonstrates the name translation api.
 */
@SuppressWarnings({"java:S1166", "java:S2221", "java:S106"})
public final class NameTranslationExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new NameTranslationExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String translatedNameData = "معمر محمد أبو منيار القذاف";
        NameTranslationRequest request = NameTranslationRequest.builder()
                .name(translatedNameData)
                .targetLanguage(LanguageCode.ENGLISH)
                .build();

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                                    .key(getApiKeyFromSystemProperty())
                                    .url(getAltUrlFromSystemProperty())
                                    .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        NameTranslationResponse response = rosetteApi.perform(NAME_TRANSLATION_SERVICE_PATH, request, NameTranslationResponse.class);
        System.out.println(responseToJson(response));
    }
}
