/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.LanguageCode;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.TranslatedNameResult;

import java.io.IOException;
import java.net.URISyntaxException;

public final class NameTranslationExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets translated names as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        setKey();
        prepareOptions(args);
        setServiceUrl();
        doNameTranslation("John Doe", LanguageCode.kor);
    }

    /**
     * Sends name translation request.
     * @param name
     * @param targetLanguage
     */
    private static void doNameTranslation(String name, LanguageCode targetLanguage) {
        try {
            NameTranslationRequest nameTranslationRequest = new NameTranslationRequest(name, null, null, null, null, targetLanguage, null, null);
            NameTranslationResponse response = rosetteAPI.translateName(nameTranslationRequest);
            print(name, response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints name translation response.
     * @param name
     * @param response
     */
    private static void print(String name, NameTranslationResponse response) {
        System.out.println(response.getRequestId());
        TranslatedNameResult result = response.getResult();
        System.out.println("name: " + name + ", "
                + "sourceScript: " + result.getSourceScript() + ", "
                + "sourceLanguageOfOrigin: " + result.getSourceLanguageOfOrigin() + ", "
                + "sourceLanguageOfUse: " + result.getSourceLanguageOfUse() + ", "
                + "translation: " + result.getTranslation() + ", "
                + "targetLanguage: " + result.getTargetLanguage() + ", "
                + "targetScript: " + result.getTargetScript() + ", "
                + "targetScheme: " + result.getTargetScheme() + ", "
                + "confidence: " + result.getConfidence());
        System.out.println();
    }
}
