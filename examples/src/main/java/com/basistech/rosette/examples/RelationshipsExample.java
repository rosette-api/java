/*
* Copyright 2024 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.RelationshipsOptions;
import com.basistech.rosette.apimodel.RelationshipsResponse;

import java.io.IOException;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.RELATIONSHIPS_SERVICE_PATH;

/**
 * Example which demonstrates the relationships extraction api.
 */
@SuppressWarnings({"java:S1166", "java:S2221", "java:S106"})
public final class RelationshipsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new RelationshipsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String relationshipsTextData = "FLIR Systems is headquartered in Oregon and produces thermal imaging, night vision, and infrared cameras and sensor systems.  According to the SEC’s order instituting a settled administrative proceeding, FLIR entered into a multi-million dollar contract to provide thermal binoculars to the Saudi government in November 2008.  Timms and Ramahi were the primary sales employees responsible for the contract, and also were involved in negotiations to sell FLIR’s security cameras to the same government officials.  At the time, Timms was the head of FLIR’s Middle East office in Dubai.";

        HttpRosetteAPI api = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        DocumentRequest<RelationshipsOptions> request = DocumentRequest.<RelationshipsOptions>builder().content(relationshipsTextData).build();
        RelationshipsResponse response = api.perform(RELATIONSHIPS_SERVICE_PATH, request, RelationshipsResponse.class);
        System.out.println(responseToJson(response));
    }
}
