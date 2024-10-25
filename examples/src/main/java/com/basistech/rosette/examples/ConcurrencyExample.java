/*
 * Copyright 2023-2024 Basis Technology Corp.
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

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.api.MorphologicalFeature;
import com.basistech.rosette.api.RosetteRequest;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameDeduplicationResponse;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.TransliterationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.ENTITIES_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.LANGUAGE_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.MORPHOLOGY_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.NAME_DEDUPLICATION_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.TOKENS_SERVICE_PATH;
import static com.basistech.rosette.api.common.AbstractRosetteAPI.TRANSLITERATION_SERVICE_PATH;

/**
 * Example which demonstrates the usage of concurrent requests
 */
public final class ConcurrencyExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new ConcurrencyExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private  void run() throws IOException, ExecutionException, InterruptedException {
        //Setting up the Api
        int maximumConcurrency = 3;
        HttpRosetteAPI api = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .connectionConcurrency(maximumConcurrency)
                .build();

        List<RosetteRequest> requests = new ArrayList<>();
        // Setting up entities request
        String entitiesTextData =
                "The Securities and Exchange Commission today announced the leadership of the agency’s trial unit. "
                + "Bridget Fitzpatrick has been named Chief Litigation Counsel of the SEC "
                + "and David Gottesman will continue to serve as the agency’s Deputy Chief Litigation Counsel. "
                + "Since December 2016, Ms. Fitzpatrick and Mr. Gottesman have served as Co-Acting Chief Litigation Counsel.  "
                + "In that role, they were jointly responsible for supervising the trial unit at the agency’s Washington D.C. headquarters "
                + "as well as coordinating with litigators in the SEC’s 11 regional offices around the country.";
        requests.add(
            api.createRosetteRequest(ENTITIES_SERVICE_PATH,
                    DocumentRequest.<EntitiesOptions>builder().content(entitiesTextData).build(),
                    EntitiesResponse.class)
        );
        // Setting up language request
        String languageData = "Por favor Señorita, says the man.";
        requests.add(
                api.createRosetteRequest(LANGUAGE_SERVICE_PATH,
                        DocumentRequest.<LanguageOptions>builder().content(languageData).build(),
                        LanguageResponse.class)
        );
        // Setting up morphology request
        // No content is given to this request and it will return an error response
        requests.add(
                api.createRosetteRequest(MORPHOLOGY_SERVICE_PATH + "/" + MorphologicalFeature.COMPLETE,
                        DocumentRequest.<MorphologyOptions>builder().build(),
                        MorphologyResponse.class)
        );
        //Setting up names deduplication request
        String nameDedupeData = "John Smith,Johnathon Smith,Fred Jones";
        List<String> listOfNames = new ArrayList<>(Arrays.asList(nameDedupeData.split(",")));

        ArrayList<Name> names = new ArrayList<>();
        for (String name: listOfNames) {
            names.add(Name.builder().text(name).build());
        }
        double threshold = 0.75;
        requests.add(
                api.createRosetteRequest(NAME_DEDUPLICATION_SERVICE_PATH,
                        NameDeduplicationRequest.builder().names(names).threshold(threshold).build(),
                        NameDeduplicationResponse.class)
        );
        //Setting up the tokens request
        String tokensData = "北京大学生物系主任办公室内部会议";
        requests.add(
                api.createRosetteRequest(TOKENS_SERVICE_PATH,
                        DocumentRequest.builder().content(tokensData).build(),
                        TokensResponse.class)
        );
        //Setting up the transliteration request
        String transliterationData = "ana r2ye7 el gam3a el sa3a 3 el 3asr";
        requests.add(
                api.createRosetteRequest(TRANSLITERATION_SERVICE_PATH,
                        DocumentRequest.builder().content(transliterationData).build(),
                        TransliterationResponse.class)
        );

        // start the threads
        ExecutorService threadPool = Executors.newFixedThreadPool(maximumConcurrency);
        List<Future<Response>> futures = threadPool.invokeAll(requests);

        // wait for the threads to finish
        for (int i = 0; i < requests.size(); i++) {
            futures.get(i).get();
        }

        for (int i = 0; i < requests.size(); i++) {
            System.out.println(responseToJson(requests.get(i).getResponse()));
        }
        threadPool.shutdown();
    }



}
