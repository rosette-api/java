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

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Example which demonstrates the sentiment api.
 */
public final class SentimentExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SentimentExample().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        // the temp file substitutes for an actual disk file.
        String sentimentFileData = "<html><head><title>New Ghostbusters Film</title></head><body><p>Original Ghostbuster Dan Aykroyd, who also co-wrote the 1984 Ghostbusters film, couldn’t be more pleased with the new all-female Ghostbusters cast, telling The Hollywood Reporter, “The Aykroyd family is delighted by this inheritance of the Ghostbusters torch by these most magnificent women in comedy.”</p></body></html>";
        try (InputStream inputStream = Files.newInputStream(createTempDataFile(sentimentFileData))) {
            HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                    .key(getApiKeyFromSystemProperty())
                    .url(getAltUrlFromSystemProperty())
                    .build();
            //The api object creates an http client, but to provide your own:
            //api.httpClient(CloseableHttpClient)
            // When no options, use <?>.
            DocumentRequest<SentimentOptions> request = new DocumentRequest.Builder<SentimentOptions>().contentBytes(inputStream, "text/html").build();
            SentimentResponse response = rosetteApi.perform(HttpRosetteAPI.SENTIMENT_SERVICE_PATH, request, SentimentResponse.class);
            System.out.println(responseToJson(response));
        }

    }

    private static Path createTempDataFile(String data) throws IOException {
        Path file = Files.createTempFile("example.", ".html");
        try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(data);
        }
        file.toFile().deleteOnExit();
        return file;
    }
}
