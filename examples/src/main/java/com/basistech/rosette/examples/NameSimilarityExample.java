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
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;

import java.io.IOException;

/**
 * Example which demonstrates name similarity.
 */
public final class NameSimilarityExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new NameSimilarityExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String matchedNameData1 = "Michael Jackson";
        String matchedNameData2 = "迈克尔·杰克逊";
        Name name1 = new Name(matchedNameData1, "PERSON", ISO15924.Zyyy, LanguageCode.ENGLISH);
        Name name2 = new Name(matchedNameData2);
        RosetteAPI rosetteApi = new RosetteAPI.Builder()
                                    .apiKey(getApiKeyFromSystemProperty())
                                    .alternateUrl(getAltUrlFromSystemProperty())
                                    .build();
        NameSimilarityResponse response = rosetteApi.getNameSimilarity(new NameSimilarityRequest(name1, name2));
        System.out.println(responseToJson(response));
    }
}
