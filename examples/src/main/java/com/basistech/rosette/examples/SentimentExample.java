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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.SentimentResponse;

/**
 * Example which demonstrates the sentiment api.
 */
public final class SentimentExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            new SentimentExample().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, RosetteAPIException {
        String sentimentFileFata = "<html><head><title>New Ghostbusters Film</title></head><body><p>Original Ghostbuster Dan Aykroyd, who also co-wrote the 1984 Ghostbusters film, couldn’t be more pleased with the new all-female Ghostbusters cast, telling The Hollywood Reporter, “The Aykroyd family is delighted by this inheritance of the Ghostbusters torch by these most magnificent women in comedy.”</p></body></html>";
        File file = createTempDataFile(sentimentFileFata);
        FileInputStream inputStream = new FileInputStream(file);

        RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty(), getAltUrlFromSystemProperty());
        SentimentResponse response = rosetteApi.getSentiment(inputStream, "text/html", null, null);
        inputStream.close();
        System.out.println(responseToJson(response));
    }

    private static File createTempDataFile(String data) throws IOException {
        File file = File.createTempFile("rosette-", "-api");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8
        ));
        bw.write(data);
        bw.close();
        file.deleteOnExit();
        return file;
    }
}
