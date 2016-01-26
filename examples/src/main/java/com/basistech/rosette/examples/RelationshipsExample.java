package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.RelationshipsResponse;

/**
 * Example which demonstrates the entity extraction api.
 */
public final class RelationshipsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String relationships_text_data = "Bill Murray is in the new Ghostbusters film!";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            RelationshipsResponse response = rosetteApi.getRelationships(relationships_text_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
