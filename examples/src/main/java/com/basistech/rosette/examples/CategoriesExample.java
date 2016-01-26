package com.basistech.rosette.examples;

import java.net.URL;

import com.basistech.rosette.api.RosetteAPI;
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
            String categories_url_data = "http://www.onlocationvacations.com/2015/03/05/the-new-ghostbusters-movie-begins-filming-in-boston-in-june/";
            URL docUrl = new URL(categories_url_data);

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            CategoriesResponse response = rosetteApi.getCategories(docUrl, null, null);
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
