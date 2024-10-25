/*
* Copyright 2022-2024 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.DocumentRequest;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.CATEGORIES_SERVICE_PATH;

/**
 * Example which demonstrates the category api.
 *
 * Gets QAG categories (http://www.iab.net/QAGInitiative/overview/taxonomy) of a web page document
 * located at http://www.basistech.com/about
 */
@SuppressWarnings({"java:S1166", "java:S2221", "java:S106"})
public final class CategoriesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new CategoriesExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String categoriesTextData = "If you are a fan of the British television series Downton Abbey and you are planning to be in New York anytime before April 2nd, there is a perfect stop for you while in town.";
        HttpRosetteAPI api = new HttpRosetteAPI.Builder()
                                .key(getApiKeyFromSystemProperty())
                                .url(getAltUrlFromSystemProperty())
                                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        DocumentRequest<CategoriesOptions> request = DocumentRequest.<CategoriesOptions>builder()
                                                                    .content(categoriesTextData)
                                                                    .build();
        CategoriesResponse response = api.perform(CATEGORIES_SERVICE_PATH, request, CategoriesResponse.class);
        System.out.println(responseToJson(response));
    }
}
