package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the complete morphology api.
 */
public final class MorphologyCompleteExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String morphology_complete_data= "The quick brown fox jumped over the lazy dog. Yes he did.";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE,
                    morphology_complete_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
