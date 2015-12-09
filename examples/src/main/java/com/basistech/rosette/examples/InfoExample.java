package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.InfoResponse;

/**
 * Example which demonstrates the top level info api.
 */
public final class InfoExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            InfoResponse response = rosetteApi.getInfo();
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
