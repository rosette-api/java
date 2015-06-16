package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.LinkedEntity;
import com.basistech.rosette.apimodel.LinkedEntityResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public final class EntitiesLinkedExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets linked entities as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        String key = "";
        if (args.length == 2) {
            if (args[0].equals("--key")) {
                key = args[1];
            }
        } else {
            usage();
        }
        init(key, null);
        doLinkedEntity(text);
    }

    /**
     * Sends string LinkedEntity request.
     * @param text
     */
    private static void doLinkedEntity(String text) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getLinkedEntity(text, null, null);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

        /**
         * Prints LinkedEntityResponse.
         * @param entityResponse
         */
    private static void print(LinkedEntityResponse entityResponse) {
        System.out.println(entityResponse.getRequestId());
        for (LinkedEntity entity : entityResponse.getEntities()) {
            System.out.printf("%s\t%d\t%s\t%f\n",
                    entity.getEntityId(),
                    entity.getIndocChainId(),
                    entity.getMention(),
                    entity.getConfidence());
        }
        System.out.println();
    }
}
