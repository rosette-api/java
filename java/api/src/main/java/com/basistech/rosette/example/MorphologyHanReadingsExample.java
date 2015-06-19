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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.MorphologyResponse;

public class MorphologyHanReadingsExample extends AbstractExample {

    public MorphologyHanReadingsExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
    }
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets han readings as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new MorphologyHanReadingsExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        doMorphology(text);
    }

    /**
     * Sends morphology han readings request from text.
     * @param text
     */
    private void doMorphology(String text) {
        try {
            MorphologyResponse response = rosetteAPI.getMorphology(RosetteAPI.MorphologicalFeature.HAN_READINGS, text, null, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints morphology han readings response.
     * @param response
     */
    private static void print(MorphologyResponse response) {
        System.out.println(response.getRequestId());
        String result = "\n";
        if (response.getHanReadings() != null) {
            for (HanReadings reading : response.getHanReadings()) {
                result += reading.getText() + "\t" + reading.getHanReadings() + "\n";
            }
        }
        result += "\n";
        System.out.println(result);
    }
}
