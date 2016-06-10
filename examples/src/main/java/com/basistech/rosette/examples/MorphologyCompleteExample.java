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

import com.basistech.rosette.api.MorphologicalFeature;
import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.MorphologyResponse;

import java.io.IOException;

/**
 * Example which demonstrates the complete morphology api.
 */
public final class MorphologyCompleteExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new MorphologyCompleteExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String morphologyCompleteData = "The quick brown fox jumped over the lazy dog. Yes he did.";

        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        rosetteApi.setOptions(null);
        rosetteApi.setLanguage(null + "");
        MorphologyResponse response = rosetteApi.getMorphology(MorphologicalFeature.COMPLETE,
                morphologyCompleteData);
        System.out.println(responseToJson(response));
    }
}
