/*
* Copyright 2014 Basis Technology Corp.
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

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.TokensResponse;

import java.io.IOException;

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

    private void run() throws IOException, RosetteAPIException {
        String tokensData = "北京大学生物系主任办公室内部会议";

        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        TokensResponse response = rosetteApi.getTokens(tokensData);
        System.out.println(responseToJson(response));
    }
}
