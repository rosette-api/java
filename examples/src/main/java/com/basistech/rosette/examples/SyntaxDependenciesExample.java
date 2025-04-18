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
import com.basistech.rosette.apimodel.SyntaxDependenciesResponse;
import com.basistech.rosette.apimodel.DocumentRequest;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.SYNTAX_DEPENDENCIES_SERVICE_PATH;

/**
 * Example which demonstrates the syntax dependencies endpoint of the Analytics api.
 */
@SuppressWarnings({"java:S1166", "java:S2221", "java:S106"})
public final class SyntaxDependenciesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SyntaxDependenciesExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String syntaxDependenciesData = "Yoshinori Ohsumi, a Japanese cell biologist, was awarded the Nobel Prize in Physiology or Medicine on Monday.";
        HttpRosetteAPI api = new HttpRosetteAPI.Builder()
                                .key(getApiKeyFromSystemProperty())
                                .url(getAltUrlFromSystemProperty())
                                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        DocumentRequest<?> request = DocumentRequest.builder().content(syntaxDependenciesData).build();
        SyntaxDependenciesResponse response = api.perform(SYNTAX_DEPENDENCIES_SERVICE_PATH, request, SyntaxDependenciesResponse.class);
        System.out.println(responseToJson(response));
    }
}
