package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameMatcherRequest;
import com.basistech.rosette.apimodel.NameMatcherResponse;
import com.basistech.rosette.apimodel.NameMatcherResult;

import java.io.IOException;
import java.net.URISyntaxException;

public final class NameMatcherExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets name matching as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doNameMatcher("John Doe", "Jon Doe");
    }
    
    /**
     * Sends NameMatcherRequest.
     * @param name1
     * @param name2
     */
    private static void doNameMatcher(String name1, String name2) {
        try {
            NameMatcherRequest request = new NameMatcherRequest(new Name(name1), new Name(name2));
            NameMatcherResponse response = rosetteAPI.matchName(request);
            print(request, response);
        } catch (RosetteAPIException | IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints NameMatcherResponse.
     * @param request
     * @param response
     */
    private static void print(NameMatcherRequest request, NameMatcherResponse response) {
        System.out.println(response.getRequestId());
        NameMatcherResult result = response.getResult();
        System.out.printf("%s\t%s\t%f\n", request.getName1().getText(), request.getName2().getText(), result.getScore());
        System.out.println();
    }
}
