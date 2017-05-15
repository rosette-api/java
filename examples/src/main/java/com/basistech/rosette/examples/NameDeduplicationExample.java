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
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameDeduplicationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example which demonstrates name deduplication.
 */
public final class NameDeduplicationExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new NameDeduplicationExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String nameDedupeData = "John Smith,Johnathon Smith,Fred Jones";

        ArrayList<Name> names = new ArrayList<>();
        for (String name: new ArrayList<String>(Arrays.asList(nameDedupeData.split(",")))) {
            names.add(new Name(name));
        }
        double threshold = 0.75;

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                                    .key(getApiKeyFromSystemProperty())
                                    .url(getAltUrlFromSystemProperty())
                                    .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        NameDeduplicationRequest request = new NameDeduplicationRequest(names, threshold);
        NameDeduplicationResponse response = rosetteApi.perform(HttpRosetteAPI.NAME_DEDUPLICATION_SERVICE_PATH, request,
                NameDeduplicationResponse.class);
        System.out.println(responseToJson(response));
    }
}
