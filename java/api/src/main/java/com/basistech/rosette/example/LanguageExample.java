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
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public final class LanguageExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets language as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doLanguage(text);
    }
    
    /**
     * Sends language request from text.
     * @param text
     */
    private static void doLanguage(String text) {
        try {
            LanguageResponse response = rosetteAPI.getLanguage(text, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints langauge response.
     * @param response
     */
    private static void print(com.basistech.rosette.apimodel.LanguageResponse response) {
        System.out.println(response.getRequestId());
        for (LanguageDetectionResult ldr : response.getLanguageDetections()) {
            System.out.printf("%s\t%f\n", ldr.getLanguage(), ldr.getConfidence());
        }
        System.out.println();
    }
}
