package com.basistech.rosette.examples;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.ISO15924;
import com.basistech.rosette.apimodel.LanguageCode;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameMatchingRequest;
import com.basistech.rosette.apimodel.NameMatchingResponse;

/**
 * Example which demonstrates the name matching api.
 */
public final class MatchedNameExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String matched_name_data1 = "Michael Jackson";
            String matched_name_data2 = "迈克尔·杰克逊";
            Name name1 = new Name(matched_name_data1, "PERSON", ISO15924.Zyyy, LanguageCode.eng);
            Name name2 = new Name(matched_name_data2);

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            NameMatchingResponse response = rosetteApi.matchName(new NameMatchingRequest(name1, name2));
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
