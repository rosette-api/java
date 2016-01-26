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
            String sentiment_file_data = "<html><head><title>New Ghostbusters Film</title></head><body><p>Original Ghostbuster Dan Aykroyd, who also co-wrote the 1984 Ghostbusters film, couldn’t be more pleased with the new all-female Ghostbusters cast, telling The Hollywood Reporter, “The Aykroyd family is delighted by this inheritance of the Ghostbusters torch by these most magnificent women in comedy.”</p></body></html>";
            File file = createTempDataFile(sentiment_file_data);
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
