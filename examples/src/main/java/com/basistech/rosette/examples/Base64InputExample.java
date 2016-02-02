/*
* Copyright 2015 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * Example which demonstrates the entity extraction api taking a base 64-encoded input.
 */
public final class Base64InputExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new Base64InputExample().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String entitiesTextData = "Bill Murray will appear in new Ghostbusters film: Dr. Peter Venkman was spotted filming a cameo in Boston this… http://dlvr.it/BnsFfS";
        byte[] encodedExample = Base64.encodeBase64(entitiesTextData.getBytes(Charsets.UTF_8));
        String text = new String(encodedExample);

        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        EntitiesResponse response = rosetteApi.getEntities(text, null, null);
        System.out.println(responseToJson(response));
    }
}
