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
import com.basistech.rosette.api.MorphologicalFeature;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyResponse;

import java.io.IOException;

/**
 * Example which demonstrates the lemmas api.
 */
public final class MorphologyLemmasExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new MorphologyLemmasExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String morphologyLemmasData = "The fact is that the geese just went back to get a rest and I'm not banking on their return soon";
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        DocumentRequest<MorphologyOptions> request = DocumentRequest.<MorphologyOptions>builder().content(morphologyLemmasData)
                .build();
        MorphologyResponse response = rosetteApi.perform(HttpRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/" + MorphologicalFeature.LEMMAS,
                request, MorphologyResponse.class);
        System.out.println(responseToJson(response));
    }
}
