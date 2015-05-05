import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

    public static void main(String[] args) {
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

        testLanguageRequestFromUrl(url);
        testLanguageRequestFromText(text);
        testLanguageRequestFromFile(new File("Chinese.txt"));
        testLanguageRequestFromFile(new File("FileNotFound.txt"));
        testLanguageRequestFromFile(new File("src/main/resources"));
        testLanguageRequestFromFile(new File("Numbers.txt"));

        testEntityRequestFromUrl(url);
        testEntityRequestFromText(text);
        testEntityRequestFromFile(new File("Chinese.txt"));
        testEntityRequestFromFile(new File("NoEntity.txt"));

        testResolvedEntityFromUrl(url);
        testResolvedEntityFromText(text);
        testResolvedEntityFromFile(new File("Chinese.txt"));

        testSentimentFromUrl(url, new SentOptions(SentimentModel.REVIEW, true));
        testSentimentFromText(text);
        testSentimentFromText(text, new SentOptions(SentimentModel.SHORT_STRING, true));
        testSentimentFromFile(new File("Chinese.txt"));
        testSentimentFromFile(new File("English.txt"));
        testSentimentFromFile(new File("English.txt"), new SentOptions(SentimentModel.REVIEW, true));
    }

    private static void testSentimentFromFile(File file) {
        testSentimentFromFile(file, null);
    }

    private static void testSentimentFromFile(File file, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentimentFromFile(file, options);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testSentimentFromText(String text) {
        testSentimentFromText(text, null);
    }

    private static void testSentimentFromText(String text, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentimentFromText(text, options);
            print(sentimentResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testSentimentFromUrl(URL url) {
        testSentimentFromUrl(url, null);
    }

    private static void testSentimentFromUrl(URL url, SentOptions options) {
        try {
            SentimentResponse sentimentResponse = rosetteAPI.getSentimentFromUrl(url, options);
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

    private static void testResolvedEntityFromFile(File file) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntityFromFile(file);
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

    private static void testResolvedEntityFromText(String text) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntityFromText(text);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testResolvedEntityFromUrl(URL url) {
        try {
            LinkedEntityResponse entityResponse = rosetteAPI.getResolvedEntityFromUrl(url);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testEntityRequestFromFile(File file) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntityFromFile(file);
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

    private static void testEntityRequestFromText(String text) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntityFromText(text);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testEntityRequestFromUrl(URL url) {
        try {
            EntityResponse entityResponse = rosetteAPI.getEntityFromUrl(url);
            print(entityResponse);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequestFromFile(File file) {
        try {
            LanguageResponse response = rosetteAPI.getLanguageFromFile(file);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequestFromText(String text) {
        try {
            LanguageResponse response = rosetteAPI.getLanguageFromText(text);
            System.out.println(response.toString());
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        }
    }

    private static void testLanguageRequestFromUrl(URL url) {
        try {
            LanguageResponse response = rosetteAPI.getLanguageFromUrl(url);
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
