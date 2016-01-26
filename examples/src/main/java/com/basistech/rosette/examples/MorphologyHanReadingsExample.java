package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.MorphologyResponse;

/**
 * Example which demonstrates the han readings api.
 */
public final class MorphologyHanReadingsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String morphology_han_readings_data = "北京大学生物系主任办公室内部会议";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            MorphologyResponse response = rosetteApi.getMorphology(RosetteAPI.MorphologicalFeature.HAN_READINGS,
                    morphology_han_readings_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
