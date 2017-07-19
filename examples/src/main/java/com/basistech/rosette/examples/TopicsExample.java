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

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.TopicsResponse;
import com.basistech.util.LanguageCode;

public class TopicsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new TopicsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws Exception {
        String sampleData = "Bayonetta.  Bayonetta.  Bayonetta.  Bayonetta.  Bayonetta.";
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        DocumentRequest request = new DocumentRequest.Builder()
                .language(LanguageCode.ENGLISH)
                .content(sampleData)
                .build();
        TopicsResponse resp = rosetteApi.perform(HttpRosetteAPI.TOPICS_SERVICE_PATH, request, TopicsResponse.class);
        System.out.println(responseToJson(resp));
    }
}
