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
import java.net.URISyntaxException;
import java.util.Date;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.PingResponse;

public final class PingExample extends AbstractExample {
    
    public PingExample() {}
    
    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets categories as a demonstration of usage.
     *
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        new PingExample().run(args);
    }

    @Override
    protected void run(String[] args) {
        super.run(args);
        doPing();
    }
    
    /**
     * Pings Rosette API.
     */
    private void doPing() {
        try {
            PingResponse pingResponse = rosetteAPI.ping();
            print(pingResponse);
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints ping response.
     * @param pingResponse
     */
    private static void print(PingResponse pingResponse) {
        System.out.printf("Message: %s\tTime: %s\n",
                pingResponse.getMessage(),
                new Date(pingResponse.getTime()).toString()
        );
        System.out.println();
    }
}
