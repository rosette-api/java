package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the decompounding api.
 */
public final class MorphologyCompoundComponentsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "Rechtsschutzversicherungsgesellschaften";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.COMPOUND_COMPONENTS,
                    text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
