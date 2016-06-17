/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.basistech.rosette.examples;

import java.io.IOException;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.CategoriesResponse;

/**
 * Example which demonstrates the category api.
 *
 * Gets QAG categories (http://www.iab.net/QAGInitiative/overview/taxonomy) of a web page document
 * located at http://www.basistech.com/about
 */
public final class CategoriesExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new CategoriesExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String categoriesUrlData = "http://www.onlocationvacations.com/2015/03/05/the-new-ghostbusters-movie-begins-filming-in-boston-in-june/";
        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        rosetteApi.setOptions(null);
        CategoriesResponse response = rosetteApi.getCategories(categoriesUrlData);
        System.out.println(responseToJson(response));
    }
}
