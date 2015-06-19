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
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameMatcherRequest;
import com.basistech.rosette.apimodel.NameMatcherResponse;
import com.basistech.rosette.apimodel.NameMatcherResult;

/**
 * Example which demonstrates the name matcher endpoint
 */
public final class NameMatcherExample extends AbstractExample {
    
    public NameMatcherExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
    }
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets name matching as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new NameMatcherExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        doNameMatcher("John Doe", "Jon Doe");
    }
    
    /**
     * Sends name matcher request.
     * @param name1
     * @param name2
     */
    private void doNameMatcher(String name1, String name2) {
        try {
            NameMatcherRequest request = new NameMatcherRequest(new Name(name1), new Name(name2));
            NameMatcherResponse response = rosetteAPI.matchName(request);
            print(request, response);
        } catch (RosetteAPIException | IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints name matcher response.
     * @param request
     * @param response
     */
    private static void print(NameMatcherRequest request, NameMatcherResponse response) {
        System.out.println(response.getRequestId());
        NameMatcherResult result = response.getResult();
        System.out.printf("%s\t%s\t%f\n", request.getName1().getText(), request.getName2().getText(), result.getScore());
        System.out.println();
    }
}
