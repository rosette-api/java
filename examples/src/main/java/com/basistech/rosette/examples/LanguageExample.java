package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.LanguageResponse;

/**
 * Example which demonstrates the language detection api.
 */
public final class LanguageExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String language_data= "Por favor Señorita, says the man.";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            LanguageResponse response = rosetteApi.getLanguage(language_data, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
