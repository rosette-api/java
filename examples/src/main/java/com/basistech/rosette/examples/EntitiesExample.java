package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.EntitiesResponse;

/**
 * Example which demonstrates the entity extraction api.
 */
public final class EntitiesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String entities_text_data = "Bill Murray will appear in new Ghostbusters film: Dr. Peter Venkman was spotted filming a cameo in Boston this… http://dlvr.it/BnsFfS";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            EntitiesResponse response = rosetteApi.getEntities(entities_text_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
