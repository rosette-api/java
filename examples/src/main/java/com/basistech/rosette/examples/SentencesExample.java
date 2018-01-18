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
import com.basistech.rosette.apimodel.SentencesResponse;

import java.io.IOException;

/**
 * Example which demonstrates the sentence detection api.
 */
public final class SentencesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SentencesExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String sentencesData = "This land is your land. This land is my land, from California to the New York island; from the red wood forest to the Gulf Stream waters. This land was made for you and Me. As I was walking that ribbon of highway, I saw above me that endless skyway: I saw below me that golden valley: This land was made for you and me.";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        // When no options, use <?>.
        DocumentRequest<?> request = DocumentRequest.builder().content(sentencesData).build();
        SentencesResponse response = rosetteApi.perform(HttpRosetteAPI.SENTENCES_SERVICE_PATH, request, SentencesResponse.class);
        System.out.println(responseToJson(response));
    }
}
