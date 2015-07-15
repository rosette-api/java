package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.SentencesResponse;

/**
 * Example which demonstrates the sentence detection api.
 */
public final class SentencesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "This land is your land This land is my land\n" +
                          "From California to the New York island;\n" +
                          "From the red wood forest to the Gulf Stream waters\n\n" +

                          "This land was made for you and Me.\n\n" +

                          "As I was walking that ribbon of highway,\n" +
                          "I saw above me that endless skyway:\n" +
                          "I saw below me that golden valley:\n" +
                          "This land was made for you and me.";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            SentencesResponse response = rosetteApi.getSentences(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
