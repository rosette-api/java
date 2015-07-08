package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the part-of-speech api.
 */
public final class MorphologyPartsOfSpeechExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "The fact is that the geese just went back to get a rest and I'm not " +
                    "banking on their return soon";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.PARTS_OF_SPEECH,
                    text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
