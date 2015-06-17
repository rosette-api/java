package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.TokenResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TokenExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets tokens (words) as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doTokens(text);
    }
    
    /**
     * Sends string token request.
     * @param text
     */
    private static void doTokens(String text) {
        try {
            TokenResponse response = rosetteAPI.getTokens(text, null);
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
    private static void print(TokenResponse response) {
        System.out.println(response.getRequestId());
        for (String token : response.getTokens()) {
            System.out.println(token);
        }
        System.out.println();
    }
}
