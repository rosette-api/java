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
import com.basistech.rosette.apimodel.InfoResponse;

/**
 * Example which demonstrates the info endpoint
 */
public final class InfoExample extends AbstractExample {
    
    public InfoExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
    }
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets info as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new InfoExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        doGetInfo();
    }

    /**
     * Sends info request.
     */
    private void doGetInfo() {
        try {
            InfoResponse infoResponse = rosetteAPI.info();
            print(infoResponse);
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints info response.
     * @param infoResponse
     */
    private static void print(InfoResponse infoResponse) {
        System.out.printf("Name: %s\tVersion: %s\tBuild number: %s\tBuild time: %s\n",
                infoResponse.getName(),
                infoResponse.getVersion(),
                infoResponse.getBuildNumber(),
                infoResponse.getBuildTime()
        );
        System.out.println();
    }
}

