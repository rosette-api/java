package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.PingResponse;

/**
 * Example which demonstrates the ping api.
 */
public final class PingExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            PingResponse response = rosetteApi.ping();
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
