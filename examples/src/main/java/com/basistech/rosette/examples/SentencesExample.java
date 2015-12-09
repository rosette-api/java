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
import com.basistech.rosette.apimodel.SentencesResponse;

/**
 * Example which demonstrates the sentence detection api.
 */
public final class SentencesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "${sentences_data}";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            SentencesResponse response = rosetteApi.getSentences(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
