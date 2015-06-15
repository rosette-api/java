package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Sentiment;
import com.basistech.rosette.apimodel.SentimentResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public final class SentimentExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets sentiment as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doSentiment(text);
    }
    
    /**
     * Sends string Sentiment request with SentimentOptions.
     * @param text
     */
    private static void doSentiment(String text) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentiment(text, null, null);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints SentimentResponse.
     * @param sentimentResponse
     */
    private static void print(SentimentResponse sentimentResponse) {
        System.out.println(sentimentResponse.getRequestId());
        for (Sentiment sentiment : sentimentResponse.getSentiment()) {
            System.out.printf("%s\t%f\n", sentiment.getLabel(), sentiment.getConfidence());
            if (sentiment.getExplanations() != null) {
                for (String explanation : sentiment.getExplanations()) {
                    System.out.printf("  explanation: %s\n", explanation);
                }
            }
        }
        System.out.println();
    }
}
