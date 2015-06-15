package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public final class LanguageExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets language as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doLanguage(text);
    }
    
    /**
     * Sends string Language request.
     * @param text
     */
    private static void doLanguage(String text) {
        try {
            LanguageResponse response = rosetteAPI.getLanguage(text, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints LanguageResponse.
     * @param response
     */
    private static void print(com.basistech.rosette.apimodel.LanguageResponse response) {
        System.out.println(response.getRequestId());
        for (LanguageDetectionResult ldr : response.getLanguageDetections()) {
            System.out.printf("%s\t%f\n", ldr.getLanguage(), ldr.getConfidence());
        }
        System.out.println();
    }
}
