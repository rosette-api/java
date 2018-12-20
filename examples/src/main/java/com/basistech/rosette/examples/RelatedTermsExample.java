/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2018 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.examples;

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.RelatedTermsOptions;
import com.basistech.rosette.apimodel.RelatedTermsResponse;
import com.basistech.util.LanguageCode;
import com.google.common.collect.Lists;

import java.io.IOException;

public final class RelatedTermsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new RelatedTermsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        String relatedTermsData = "spy";

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        // When no options, use <?>.
        DocumentRequest<RelatedTermsOptions> request = DocumentRequest.<RelatedTermsOptions>builder()
                .content(relatedTermsData)
                .options(RelatedTermsOptions.builder()
                        .resultLanguages(Lists.newArrayList(LanguageCode.SPANISH, LanguageCode.GERMAN, LanguageCode.JAPANESE))
                        .build())
                .build();
        RelatedTermsResponse response = rosetteApi.perform(HttpRosetteAPI.RELATED_TERMS_SERVICE_PATH, request, RelatedTermsResponse.class);
        System.out.println(responseToJson(response));
    }
}
