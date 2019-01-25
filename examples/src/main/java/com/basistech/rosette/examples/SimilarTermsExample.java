/*
 * Copyright 2019 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.SimilarTermsOptions;
import com.basistech.rosette.apimodel.SimilarTermsResponse;
import com.basistech.util.LanguageCode;
import com.google.common.collect.Lists;

import java.io.IOException;

/**
 * Example which demonstrates the similar terms api
 */
public final class SimilarTermsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SimilarTermsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String similarTermsData = "spy";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        // When no options, use <?>.
        DocumentRequest<SimilarTermsOptions> request = DocumentRequest.<SimilarTermsOptions>builder()
                .content(similarTermsData)
                .options(SimilarTermsOptions.builder()
                        .resultLanguages(Lists.newArrayList(LanguageCode.SPANISH, LanguageCode.GERMAN, LanguageCode.JAPANESE))
                        .build())
                .build();
        SimilarTermsResponse response = rosetteApi.perform(HttpRosetteAPI.SIMILAR_TERMS_SERVICE_PATH, request, SimilarTermsResponse.class);
        System.out.println(responseToJson(response));
    }
}
