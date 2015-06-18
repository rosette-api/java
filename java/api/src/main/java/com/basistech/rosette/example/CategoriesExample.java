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
import com.basistech.rosette.apimodel.Category;
import com.basistech.rosette.apimodel.CategoryResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * example of calling the category endpoint
 */
public final class CategoriesExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets categories as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        if (validate(args)) {
            System.out.println("url = " + args[1]);
                init(args[1]);
                doCategories(url);
        } else {
            System.out.println("no url");
            init();
            doCategories(text);
        }
    }
    
    private static boolean validate(String[] args) {
        prepareToValidate(args);
        // URL validation occurs in Rosette API, do we need this step?
        return Pattern.matches("(--url .+)?+", argsToValidate);
    }

    /**
     * Sends category request from text.
     *
     * @param text
     */
    private static void doCategories(String text) {
        try {
            CategoryResponse categoryResponse = rosetteAPI.getCategories(text, null, null);
            print(categoryResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Sends category request from URL.
     *
     * @param url
     */
    private static void doCategories(URL url) {
        try {
            CategoryResponse categoryResponse = rosetteAPI.getCategories(url, null, null);
            print(categoryResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints category response.
     *
     * @param categoryResponse
     */
    private static void print(CategoryResponse categoryResponse) {
        System.out.println(categoryResponse.getRequestId());
        for (Category category : categoryResponse.getCategories()) {
            System.out.printf("%s\t%f\t%s\n",
                    category.getLabel(),
                    category.getConfidence(),
                    category.getExplanations() == null ? "" : category.getExplanations());
        }
        System.out.println();
    }
}


