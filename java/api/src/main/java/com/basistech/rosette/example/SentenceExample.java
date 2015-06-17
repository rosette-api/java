package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.SentenceResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public final class SentenceExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets sentences as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doSentences(text);
    }

    /**
     * Sends string sentences request.
     * @param text
     */
    private static void doSentences(String text) {
        try {
            SentenceResponse response = rosetteAPI.getSentences(text, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints TokensResponse.
     * @param response
     */
    private static void print(SentenceResponse response) {
        System.out.println(response.getRequestId());
        for (String sentence : response.getSentences()) {
            System.out.println(sentence);
        }
        System.out.println();
    }
}
