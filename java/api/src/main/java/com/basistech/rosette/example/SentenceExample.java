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

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.SentenceResponse;

/**
 * Example which demonstrates the sentence endpoint
 */
public final class SentenceExample extends AbstractExample {
    
    public SentenceExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
    }
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets sentences as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new SentenceExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        doSentences(text);
    }

    /**
     * Sends sentences request from text.
     * @param text
     */
    private void doSentences(String text) {
        try {
            SentenceResponse response = rosetteAPI.getSentences(text, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints sentence response.
     * @param response
     */
    private static void print(SentenceResponse response) {
        System.out.println(response.getRequestId());
        for (String sentence : response.getSentences()) {
            System.out.println(sentence);
        }
        System.out.println();
    }
}
