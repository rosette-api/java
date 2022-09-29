/*
* Copyright 2019-2022 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.SemanticVectorsOptions;
import com.basistech.rosette.apimodel.SemanticVectorsResponse;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.SEMANTIC_VECTORS_SERVICE_PATH;

/**
 * Example which demonstrates semantic-vectors.
 */
public final class SemanticVectorsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SemanticVectorsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String semanticVectorsData = "Cambridge, Massachusetts";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        // When no options, use <?>.
        DocumentRequest<SemanticVectorsOptions> request = DocumentRequest.<SemanticVectorsOptions>builder()
            .content(semanticVectorsData)
            .build();
        SemanticVectorsResponse response = rosetteApi.perform(SEMANTIC_VECTORS_SERVICE_PATH, request, SemanticVectorsResponse.class);
        System.out.println(responseToJson(response));
    }
}
