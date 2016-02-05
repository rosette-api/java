/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.basistech.rosette.examples;

import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Provides examples on how to use the {@link com.basistech.rosette.api.RosetteAPI RosetteAPI}
 */
public abstract class ExampleBase {
    private static final String KEY_PROP_NAME = "rosette.api.key";
    private static final String URL_PROP_NAME = "rosette.api.altUrl";
    private static final String USAGE_STR = "Usage: java -cp rosette-api-examples.jar:lib/rosette-api-manifest.jar "
            + "-D" + KEY_PROP_NAME + "=<required_api_key> " + "-D" + URL_PROP_NAME + "=<optional_alternate_url> ";

    /**
     * Gets api key using system property {@value #KEY_PROP_NAME}
     */
    protected String getApiKeyFromSystemProperty() {
        String apiKeyStr = System.getProperty(KEY_PROP_NAME);
        if (apiKeyStr == null || apiKeyStr.trim().length() < 1) {
            showUsage(getClass());
            System.exit(1);
        }
        return apiKeyStr.trim();
    }

    /**
     * Gets alternate url using system property {@value #URL_PROP_NAME}
     */
    protected String getAltUrlFromSystemProperty() {
        String altUrlStr = System.getProperty(URL_PROP_NAME);
        if (altUrlStr == null || altUrlStr.trim().length() < 1) {
            altUrlStr = "https://api.rosette.com/rest/v1";
        }
        return altUrlStr.trim();
    }

    /**
     * Prints out how to run the program
     */
    protected static void showUsage(Class<? extends ExampleBase> commandClass) {
        System.err.println(USAGE_STR + commandClass.getName());
    }

    /**
     * Converts a response to JSON string
     *
     * @param response {@link com.basistech.rosette.apimodel.Response Response} from RosetteAPI
     */
    protected static String responseToJson(Response response) throws JsonProcessingException {
        ObjectMapper mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(response);
    }
}



