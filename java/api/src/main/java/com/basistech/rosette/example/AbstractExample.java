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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides examples on how to use the {@link com.basistech.rosette.api RosetteAPI} endpoints.
 */
public abstract class AbstractExample {

    protected static RosetteAPI rosetteAPI;
    protected static String argsToValidate;
    //protected static Map<String,String> argsToValidate = new HashMap<>();
    // default values
    protected static URL url;
    protected static String text = "The first men to reach the moon – Mr. Armstrong and his co-pilot, " +
            "Col. Edwin E. Aldrin, Jr. of the Air Force – brought their ship to rest on a level, rock-strewn plain " +
            "near the southwestern shore of the arid Sea of Tranquility.";

    /**
     * helper method for validate in subclasses
     * @param args
     */
    protected static void prepareOptions(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            sb.append(" "); // space delimiter
        }
        argsToValidate = sb.toString();
    }

    /**
     * Usage
     */
    protected static void usage() {
        System.out.println("Usage: java -cp <path-to-java-rosette-api-jar> -Drosette.api.key=<api-key> " +
                "com.basistech.rosette.example.<example>");
    }

    protected static void setKey() {
        String key = System.getProperty("rosette.api.key");
        if (key == null) {
            usage();
            return;
        }
        rosetteAPI = new RosetteAPI(key);
    }
    
    protected static void setServiceUrl() {
        Pattern p = Pattern.compile("-service-url\\s([^\\s])++");
        Matcher m = p.matcher(argsToValidate);
        if (m.find()) {
            System.out.println(m.group());
            rosetteAPI.setUrlBase(m.group().substring(13));
        } else {
            System.out.println("No service url provided, using default");
        }
    }
    
    protected static void setUrl(String address) {
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
    } 
}



