package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Category;
import com.basistech.rosette.apimodel.CategoryResponse;

import java.io.IOException;
import java.net.URISyntaxException;

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
        String key = "";
        if (args.length == 2) {
            if (args[0].equals("--key")) {
                key = args[1];
            }
        } else {
            usage();
        }
        init(key, null);
        doCategories(text);
    }

    /**
     * Sends URL category request with options.
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
     * Prints CategoryResponse.
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


