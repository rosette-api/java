package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.SentencesResponse;

/**
 * Example which demonstrates the sentence detection api.
 */
public final class SentencesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String sentences_data = "This land is your land. This land is my land\nFrom California to the New York island;\nFrom the red wood forest to the Gulf Stream waters\n\nThis land was made for you and Me.\n\nAs I was walking that ribbon of highway,\nI saw above me that endless skyway:\nI saw below me that golden valley:\nThis land was made for you and me.";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            SentencesResponse response = rosetteApi.getSentences(sentences_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
