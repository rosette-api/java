package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.SentencesResponse;

/**
 * Example which demonstrates the sentence detection api.
 */
public final class SentencesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "${sentences_data}";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            SentencesResponse response = rosetteApi.getSentences(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
