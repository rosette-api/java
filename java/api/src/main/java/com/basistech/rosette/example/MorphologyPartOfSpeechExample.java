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

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.PartOfSpeech;

import java.io.IOException;
import java.net.URISyntaxException;

public class MorphologyPartOfSpeechExample extends AbstractExample {
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets complete morphology analysis as a demonstration of usage.
     *
     * @param args not used
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doMorphology(text);
    }

    /**
     * Sends morphology part of speech request.
     * @param text
     */
    private static void doMorphology(String text) {
        try {
            MorphologyResponse response = rosetteAPI.getMorphology(RosetteAPI.MorphologicalFeature.PARTS_OF_SPEECH, text, null, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints morphology part of speech response.
     * @param response
     */
    private static void print(MorphologyResponse response) {
        System.out.println(response.getRequestId());
        String result = "\n";
        if (response.getPosTags() != null) {
            for (PartOfSpeech partOfSpeech : response.getPosTags()) {
                result += partOfSpeech.getText() + "\t" + partOfSpeech.getPos() + "\n";
            }
        }
        result += "\n";
        System.out.println(result);
    }
}
