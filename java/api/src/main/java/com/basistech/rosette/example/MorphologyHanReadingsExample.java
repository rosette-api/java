package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.MorphologyResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class MorphologyHanReadingsExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets han readings as a demonstration of usage.
     *
     * @param args not used
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doMorphology(text);
    }

    /**
     * Sends string Morphology request.
     * @param text
     */
    private static void doMorphology(String text) {
        try {
            MorphologyResponse response = rosetteAPI.getMorphology(RosetteAPI.MorphologicalFeature.HAN_READINGS, text, null, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints MorphologyResponse.
     * @param response
     */
    private static void print(MorphologyResponse response) {
        System.out.println(response.getRequestId());
        String result = "\n";
        if (response.getHanReadings() != null) {
            for (HanReadings reading : response.getHanReadings()) {
                result += reading.getText() + "\t" + reading.getHanReadings() + "\n";
            }
        }
        result += "\n";
        System.out.println(result);
    }
}
