package com.basistech.rosette.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.apimodel.SentimentResponse;

/**
 * Example which demonstrates the sentiment api.
 */
public final class SentimentExample extends ExampleBase {
    public static void main(String[] args) {
        try {
            String html = "<html><head><title>Performance Report</title></head>" +
                    "<body><p>This article is clean, concise, and very easy to read.</p></body></html>";
            File file = createTempDataFile(html);
            FileInputStream inputStream = new FileInputStream(file);

            RosetteAPI rosetteApi = new RosetteAPI(getApiKeyFromSystemProperty());
            SentimentResponse response = rosetteApi.getSentiment(inputStream, null, null);
            inputStream.close();
            System.out.println(responseToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File createTempDataFile(String data) throws IOException {
        File file = File.createTempFile("rosette-", "-api");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8
        ));
        bw.write(data);
        bw.close();
        return file;
    }
}
