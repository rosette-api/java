/*
 * Copyright 2019 Basis Technology Corp.
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

import java.io.IOException;

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.Address;
import com.basistech.rosette.apimodel.AddressSimilarityRequest;
import com.basistech.rosette.apimodel.AddressSimilarityResponse;

/**
 * Example which demonstrates address similarity
 */
public final class AddressSimilarityExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new AddressSimilarityExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String addressSimilarityHouseno1 = "1600";
        String addressSimilarityRoad1 = "Pennsylvania Ave NW";
        String addressSimilarityCity1 = "Washington";
        String addressSimilarityState1 = "DC";
        String addressSimilarityPostcode1 = "20500";
        Address address1 = Address.builder()
                .houseNumber(addressSimilarityHouseno1)
                .road(addressSimilarityRoad1)
                .city(addressSimilarityCity1)
                .state(addressSimilarityState1)
                .postCode(addressSimilarityPostcode1)
                .build();
        String addressSimilarityHouseno2 = "160";
        String addressSimilarityRoad2 = "Pennsilvana Avenue";
        String addressSimilarityCity2 = "Washington";
        String addressSimilarityState2 = "D.C.";
        String addressSimilarityPostcode2 = "20500";
        Address address2 = Address.builder()
                .houseNumber(addressSimilarityHouseno2)
                .road(addressSimilarityRoad2)
                .city(addressSimilarityCity2)
                .state(addressSimilarityState2)
                .postCode(addressSimilarityPostcode2)
                .build();
        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        AddressSimilarityRequest request = AddressSimilarityRequest.builder().address1(address1).address2(address2).build();
        AddressSimilarityResponse response = rosetteApi.perform(HttpRosetteAPI.ADDRESS_SIMILARITY_SERVICE_PATH, request, AddressSimilarityResponse.class);
        System.out.println(responseToJson(response));
    }
}
