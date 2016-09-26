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
import com.basistech.rosette.api.MorphologicalFeature;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyResponse;

import java.io.IOException;

/**
 * Example which demonstrates the han readings api.
 */
public final class MorphologyHanReadingsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new MorphologyHanReadingsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String morphologyHanReadingsData = "北京大学生物系主任办公室内部会议";
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        DocumentRequest<MorphologyOptions> request = new DocumentRequest.Builder<MorphologyOptions>().content(morphologyHanReadingsData)
                .build();
        MorphologyResponse response = rosetteApi.perform(HttpRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/" + MorphologicalFeature.HAN_READINGS,
                request, MorphologyResponse.class);
        System.out.println(responseToJson(response));
    }
}
