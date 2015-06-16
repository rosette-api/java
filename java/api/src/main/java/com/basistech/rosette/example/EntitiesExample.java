package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.EntityResponse;
import com.basistech.rosette.apimodel.ExtractedEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public final class EntitiesExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets entities as a demonstration of usage.
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
        doEntity(text);
    }

        /**
         * Sends URL Entity request.
         * @param text
         */
    private static void doEntity(String text) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntity(text, null, null);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints EntityResponse.
     * @param entityResponse
     */
    private static void print(EntityResponse entityResponse) {
        System.out.println(entityResponse.getRequestId());
        for (ExtractedEntity entity : entityResponse.getEntities()) {
            System.out.printf("%s\t%s\t%d\t%s\t%f\t%d\n",
                    entity.getNormalized(),
                    entity.getType(),
                    entity.getCount(),
                    entity.getMention(),
                    entity.getConfidence(),
                    entity.getIndocChainId());
        }
    }
}
