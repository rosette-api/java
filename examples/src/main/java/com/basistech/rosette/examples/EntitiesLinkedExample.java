package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;

/**
 * Example which demonstrates the entity linking api.
 */
public final class EntitiesLinkedExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "${entities_linked_data}";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            LinkedEntitiesResponse response = rosetteApi.getLinkedEntities(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
