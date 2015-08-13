package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the lemmas api.
 */
public final class MorphologyLemmasExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "${morphology_lemmas_data}";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.LEMMAS,
                    text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
