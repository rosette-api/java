/*
* Copyright 2021-2022 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.EventsOptions;
import com.basistech.rosette.apimodel.EventsResponse;
import com.basistech.util.LanguageCode;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.EVENTS_SERVICE_PATH;

/**
 * Example which demonstrates the events API.
 */
public class EventsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new EventsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws Exception {
        String topicsData = "I am looking for flights to Super Bowl 2022 in Inglewood, LA.";
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        DocumentRequest<EventsOptions> request = DocumentRequest.<EventsOptions>builder()
                .language(LanguageCode.ENGLISH)
                .content(topicsData)
                .build();
        EventsResponse resp = rosetteApi.perform(EVENTS_SERVICE_PATH, request, EventsResponse.class);
        System.out.println(responseToJson(resp));
    }
}
