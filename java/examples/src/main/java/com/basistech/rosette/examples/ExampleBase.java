package com.basistech.rosette.examples;

import java.lang.invoke.MethodHandles;

// use these shaded classes to help print out response objects as json
import com.basistech.com.fasterxml.jackson.annotation.JsonInclude;
import com.basistech.com.fasterxml.jackson.core.JsonProcessingException;
import com.basistech.com.fasterxml.jackson.databind.ObjectMapper;
import com.basistech.com.fasterxml.jackson.databind.SerializationFeature;

import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;

/**
 * Provides examples on how to use the {@link com.basistech.rosette.api.RosetteAPI RosetteAPI}
 */
public abstract class ExampleBase {
    private static final String KEY_PROP_NAME = "rosette.api.key";
    private static final String USAGE_STR = "Usage: java -cp .:<path_to_rosette-api.jar> " +
            "-D" + KEY_PROP_NAME + "=<required_api_key> ";

    /**
     * Gets api key using system property {@value #KEY_PROP_NAME}
     */
    protected static String getApiKeyFromSystemProperty() {
        String apiKeyStr = System.getProperty(KEY_PROP_NAME);
        if (apiKeyStr == null || apiKeyStr.trim().length() < 1) {
            showUsage();
            System.exit(1);
        }
        return apiKeyStr.trim();
    }

    /**
     * Prints out how to run the program
     */
    protected static void showUsage() {
        System.err.println(USAGE_STR + MethodHandles.lookup().lookupClass());
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



