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
import com.basistech.rosette.apimodel.EntityResponse;
import com.basistech.rosette.apimodel.ExtractedEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public final class EntitiesExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets entities as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        setKey();
        prepareOptions(args);
        setServiceUrl();
        doEntity(text);
    }

        /**
         * Sends entity request from text.
         * @param text
         */
    private static void doEntity(String text) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntity(text, null, null);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints entity response
     * @param entityResponse
     */
    private static void print(EntityResponse entityResponse) {
        System.out.println(entityResponse.getRequestId());
        for (ExtractedEntity entity : entityResponse.getEntities()) {
            System.out.printf("%s\t%s\t%d\t%s\t%f\t%d\n",
                    entity.getNormalized(),
                    entity.getType(),
                    entity.getCount(),
                    entity.getMention(),
                    entity.getConfidence(),
                    entity.getIndocChainId());
        }
    }
}
