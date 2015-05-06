import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.api.RosetteAPIParameterException;
import com.basistech.rosette.model.EntityResponse;
import com.basistech.rosette.model.ExtractedEntity;
import com.basistech.rosette.model.LanguageCode;
import com.basistech.rosette.model.LanguageResponse;
import com.basistech.rosette.model.LinkedEntity;
import com.basistech.rosette.model.LinkedEntityResponse;
import com.basistech.rosette.model.NameTranslationRequest;
import com.basistech.rosette.model.NameTranslationResponse;
import com.basistech.rosette.model.SentOptions;
import com.basistech.rosette.model.Sentiment;
import com.basistech.rosette.model.SentimentModel;
import com.basistech.rosette.model.SentimentResponse;

public class APITest {

    private static final String key = "770977ec17b43a6f4d1e526b173f3a1c"; // Your Rosette API user_key
    private static RosetteAPI rosetteAPI;

    @Test
    public void testRosetteAPI() throws URISyntaxException {
        String website = "http://www.basistech.com";
        URL url = null;
        try {
            url = new URL(website);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String text = "I live in Boston, Massachusetts.";

        rosetteAPI = new RosetteAPI(key);
        rosetteAPI.setServiceUrl("http://localhost:8755/rest/v1/");

        testNameTranslationRequest("John Doe", LanguageCode.KOREAN);
        testNameTranslationRequest("John Doe", null);
        testNameTranslationRequest("", null);
        testNameTranslationRequest("John Doe", LanguageCode.SPANISH);
        testNameTranslationRequest("习近平", LanguageCode.ENGLISH);

        testLanguageRequest(url);
        testLanguageRequest(text);
        testLanguageRequest(getTestResource("Chinese.txt"));
        testLanguageRequest(getTestResource("Chinese-GBK.txt"));
        testLanguageRequest(new File("FileNotFound.txt"));
        testLanguageRequest(new File("src/main"));
        testLanguageRequest(getTestResource("Numbers.txt"));
        testLanguageRequest(getTestResource("Empty.txt"));

        testEntityRequest(url);
        testEntityRequest(text);
        testEntityRequest(getTestResource("Chinese.txt"));
        testEntityRequest(getTestResource("NoEntity.txt"));

        testResolvedEntity(url);
        testResolvedEntity(text);
        testResolvedEntity(getTestResource("Chinese.txt"));

        testSentiment(url);
        testSentiment(url, new SentOptions(SentimentModel.REVIEW, true));
        testSentiment(text);
        testSentiment(text, new SentOptions(SentimentModel.SHORT_STRING, true));
        testSentiment(getTestResource("Chinese.txt"));
        testSentiment(getTestResource("English.txt"));
        testSentiment(getTestResource("English.txt"), new SentOptions(SentimentModel.REVIEW, true));
    }

    private File getTestResource(String filename) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource(filename).toURI());
    }
    private static void testSentiment(File file) {
        testSentiment(file, null);
    }

    private static void testSentiment(File file, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentiment(file, options);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testSentiment(String text) {
        testSentiment(text, null);
    }

    private static void testSentiment(String text, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentiment(text, options);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testSentiment(URL url) {
        testSentiment(url, null);
    }

    private static void testSentiment(URL url, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentiment(url, options);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void print(SentimentResponse sentimentResponse) {
        System.out.println(sentimentResponse.getRequestId());
        for (Sentiment sentiment : sentimentResponse.getSentiment()) {
            System.out.printf("%s\t%f\n", sentiment.getLabel(), sentiment.getConfidence());
            if (sentiment.getExplanations() != null) {
                for (String explanation : sentiment.getExplanations()) {
                    System.out.printf("  explanation: %s\n", explanation);
                }
            }
        }
        System.out.println();
    }

    private static void testResolvedEntity(File file) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntity(file);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void print(LinkedEntityResponse entityResponse) {
        System.out.println(entityResponse.getRequestId());
        for (LinkedEntity entity : entityResponse.getEntities()) {
            System.out.printf("%s\t%d\t%s\t%f\n", entity.getEntityId(), entity.getIndocChainId(), entity.getMention(), entity.getConfidence());
        }
        System.out.println();
    }

    private static void testResolvedEntity(String text) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntity(text);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testResolvedEntity(URL url) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntity(url);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testEntityRequest(File file) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntity(file);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void print(EntityResponse entityResponse) {
        System.out.println(entityResponse.getRequestId());
        for (ExtractedEntity entity : entityResponse.getEntities()) {
            System.out.printf("%s\t%s\t%d\t%s\t%f\t%d\n", entity.getNormalized(), entity.getType(), entity.getCount(), entity.getMention(), entity.getConfidence(), entity.getIndocChainId());
        }
    }

    private static void testEntityRequest(String text) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntity(text);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testEntityRequest(URL url) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntity(url);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequest(File file) {
        try {
            LanguageResponse response = rosetteAPI.getLanguage(file);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequest(String text) {
        try {
            LanguageResponse response = rosetteAPI.getLanguage(text);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequest(URL url) {
        try {
            LanguageResponse response = rosetteAPI.getLanguage(url);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    private static void testNameTranslationRequest(String name, String targetLanguage) {
        try {
            NameTranslationRequest nameTranslationRequest = new NameTranslationRequest(name, null, null, null, null, targetLanguage, null, null);
            NameTranslationResponse response = rosetteAPI.translateName(nameTranslationRequest);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (RosetteAPIParameterException e) {
            System.err.println(e.toString());
        }
    }

}
