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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Category;
import com.basistech.rosette.apimodel.CategoryResponse;

/**
 * Example which demonstrates the category endpoint
 */
public final class CategoriesExample extends AbstractExample {
    
    public CategoriesExample() {
        try {
            url = new URL("http://www.basistech.com/about/");
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        }
        usage = usage + " -url <optional-url>";
    }

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
        new CategoriesExample().run(args);
    }
    
    @Override
    protected void run(String[] args) {
        super.run(args);
        setUrl();
    }

    /**
     * sets the target URL to get categories from
     */
    private void setUrl() {
        Pattern p = Pattern.compile("-url\\s[^\\s]+");
        Matcher m = p.matcher(argsToValidate);
        if (m.find()) {
            String result = m.group().substring(5);
            System.out.println("url: " + result);
            try {
                url = new URL(result);
            } catch (MalformedURLException e) {
                System.err.println(e.toString());
            }
        } else {
            System.out.println("No url provided, using default: " + url.toString());
        }
        doCategories(url);
    }

    /**
     * Sends category request from URL.
     *
     * @param url
     */
    private void doCategories(URL url) {
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


