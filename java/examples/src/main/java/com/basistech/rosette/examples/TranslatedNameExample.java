package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.LanguageCode;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;

/**
 * Example which demonstrates the name translation api.
 */
public final class TranslatedNameExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            NameTranslationRequest request = new NameTranslationRequest("معمر محمد أبو منيار القذاف",
                    null, null, null, null, LanguageCode.eng, null, null);

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            NameTranslationResponse response = rosetteApi.translateName(request);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
