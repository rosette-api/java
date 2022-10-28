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
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.TokensResponse;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.TOKENS_SERVICE_PATH;

/**
 * Example which demonstrates the tokens api.
 */
public final class TokensExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new TokensExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String tokensData = "北京大学生物系主任办公室内部会议";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        // When no options, use <?>.
        DocumentRequest<?> request = DocumentRequest.builder().content(tokensData).build();
        TokensResponse response = rosetteApi.perform(TOKENS_SERVICE_PATH, request, TokensResponse.class);
        System.out.println(responseToJson(response));
    }
}
