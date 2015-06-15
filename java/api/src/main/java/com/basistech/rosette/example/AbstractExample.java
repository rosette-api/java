package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPI;

import java.net.MalformedURLException;

/**
 * Provides examples on how to use the {@link com.basistech.rosette.api RosetteAPI} endpoints.
 */
public abstract class AbstractExample {

    protected static RosetteAPI rosetteAPI;
    protected static String website = "http://www.basistech.com";
    protected static String text = "The first men to reach the moon – Mr. Armstrong and his co-pilot, " +
            "Col. Edwin E. Aldrin, Jr. of the Air Force – brought their ship to rest on a level, rock-strewn plain " +
            "near the southwestern shore of the arid Sea of Tranquility.";
    /**
     * Usage
     */
    protected static void usage() {
        System.out.println("Usage: java -Drosette.api.key=your-api-key -jar rosette-api-example-jar-with-dependencies.jar");
    }

    protected static void init() throws MalformedURLException {
        String apiKey = System.getProperty("rosette.api.key");

        if (apiKey == null) {
            usage();
            return;
        }

        rosetteAPI = new RosetteAPI(apiKey);
    }
}



