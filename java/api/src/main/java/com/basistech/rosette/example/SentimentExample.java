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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Sentiment;
import com.basistech.rosette.apimodel.SentimentResponse;

/**
 * Example which demonstrates the sentiment endpoint
 */
public final class SentimentExample extends AbstractExample {

    private ClassLoader cl;
    
    public SentimentExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
        cl = SentimentExample.class.getClassLoader();
        file = cl.getResourceAsStream("English.txt");
        usage = usage + " -file <optional-file>";
    }
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets sentiment as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new SentimentExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        setFile();
    }

    /**
     * sets the file to get sentiment from 
     */
    private void setFile() {
        Pattern p = Pattern.compile("-file\\s[^\\s]+");
        Matcher m = p.matcher(argsToValidate);
        if (m.find()) {
            String result = m.group().substring(6);
            System.out.println("file: " + result.toString());
            file = cl.getResourceAsStream(result);
        } else {
            System.out.println("No file provided, using default: " + "English.txt");
        }
        doSentiment(file);
    }
    
    /**
     * Sends sentiment request from a file.
     * @param file
     */
    private void doSentiment(InputStream file) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentiment(text, null, null);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints sentiment response.
     * @param sentimentResponse
     */
    private static void print(SentimentResponse sentimentResponse) {
        System.out.println(sentimentResponse.getRequestId());
        for (Sentiment sentiment : sentimentResponse.getSentiment()) {
            System.out.printf("%s\t%f\n", sentiment.getLabel(), sentiment.getConfidence());
            if (sentiment.getExplanations() != null) {
                for (String explanation : sentiment.getExplanations()) {
                    System.out.printf("  explanation: %s\n", explanation);
                }
            }
        }
        System.out.println();
    }
}
