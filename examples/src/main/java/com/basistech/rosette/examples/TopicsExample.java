/*
* Copyright 2024 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.TopicsOptions;
import com.basistech.rosette.apimodel.TopicsResponse;
import com.basistech.util.LanguageCode;

import static com.basistech.rosette.api.common.AbstractRosetteAPI.TOPICS_SERVICE_PATH;

/**
 * Example which demonstrates the topics API.
 */
public class TopicsExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new TopicsExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws Exception {
        String topicsData = "Lily Collins is in talks to join Nicholas Hoult in Chernin Entertainment and Fox Searchlight's J.R.R. Tolkien biopic Tolkien. Anthony Boyle, known for playing Scorpius Malfoy in the British play Harry Potter and the Cursed Child, also has signed on for the film centered on the famed author. In Tolkien, Hoult will play the author of the Hobbit and Lord of the Rings book series that were later adapted into two Hollywood trilogies from Peter Jackson. Dome Karukoski is directing the project.";
        HttpRosetteAPI api = new HttpRosetteAPI.Builder()
                .key(getApiKeyFromSystemProperty())
                .url(getAltUrlFromSystemProperty())
                .build();
        DocumentRequest<TopicsOptions> request = DocumentRequest.<TopicsOptions>builder()
                .language(LanguageCode.ENGLISH)
                .content(topicsData)
                .build();
        TopicsResponse resp = api.perform(TOPICS_SERVICE_PATH, request, TopicsResponse.class);
        System.out.println(responseToJson(resp));
    }
}
