package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.TokensResponse;

/**
 * Example which demonstrates the tokens api.
 */
public final class TokensExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String text = "北京大学生物系主任办公室内部会议";

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            TokensResponse response = rosetteApi.getTokens(text, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
