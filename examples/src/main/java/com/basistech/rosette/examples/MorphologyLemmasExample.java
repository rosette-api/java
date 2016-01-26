package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the lemmas api.
 */
public final class MorphologyLemmasExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String morphology_lemmas_data = "The fact is that the geese just went back to get a rest and I'm not banking on their return soon";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.LEMMAS,
                    morphology_lemmas_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
