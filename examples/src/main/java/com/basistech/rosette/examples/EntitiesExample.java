package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.EntitiesResponse;

/**
 * Example which demonstrates the entity extraction api.
 */
public final class EntitiesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "${entities_data}";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            EntitiesResponse response = rosetteApi.getEntities(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
