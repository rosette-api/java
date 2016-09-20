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
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.AbstractRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesResponse;

import java.io.IOException;

/**
 * Example which demonstrates the entity extraction api.
 */
public final class EntitiesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new EntitiesExample().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String entitiesTextData = "Bill Murray will appear in new Ghostbusters film: Dr. Peter Venkman was spotted filming a cameo in Boston this… http://dlvr.it/BnsFfS";
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                                .key(getApiKeyFromSystemProperty())
                                .url(getAltUrlFromSystemProperty())
                                .build();
        DocumentRequest<EntitiesOptions> request = new DocumentRequest.Builder<EntitiesOptions>().content(entitiesTextData).build();
        EntitiesResponse response = rosetteApi.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
        System.out.println(responseToJson(response));
    }
}
