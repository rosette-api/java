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
import com.basistech.rosette.apimodel.InfoResponse;

import java.io.IOException;

/**
 * Example which demonstrates the top level info api.
 */
public final class InfoExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new InfoExample().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, RosetteAPIException {
        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
        InfoResponse response = rosetteApi.getInfo();
        System.out.println(responseToJson(response));
    }
}
