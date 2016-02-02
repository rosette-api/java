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
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String morphologyHanReadingsData = "北京大学生物系主任办公室内部会议";

        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.HAN_READINGS,
                morphologyHanReadingsData, null, null);
        System.out.println(responseToJson(response));
    }
}
