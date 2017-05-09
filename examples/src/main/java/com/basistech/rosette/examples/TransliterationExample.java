/*
* Copyright 2017 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.TransliterationOptions;
import com.basistech.rosette.apimodel.TransliterationResponse;
import com.basistech.util.LanguageCode;

import java.io.IOException;

/**
 * Example which demonstrates the transliteration API.
 */
public class TransliterationExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new TransliterationExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String transliterationData = "haza ya7taj fakat ila an takoun ba3dh el-nousous allati na7n ymkn an tata7awal ila al-3arabizi.";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        // The API object creates an HTTP client, but to provide your own:
        // HttpRosetteAPI.Builder#httpClient(CloseableHttpClient)
        DocumentRequest<TransliterationOptions> request = new DocumentRequest.Builder<TransliterationOptions>()
                .content(transliterationData)
                .language(LanguageCode.ARABIC)
                .build();
        TransliterationResponse response = rosetteApi.perform(HttpRosetteAPI.TRANSLITERATION_SERVICE_PATH, request, TransliterationResponse.class);
        System.out.println(responseToJson(response));
    }
}
