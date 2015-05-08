package com.basistech.rosette;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockserver.integration.ClientAndServer;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.model.InputUnit;
import com.basistech.rosette.model.LanguageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(Parameterized.class)
public class RosetteAPITest extends Assert {

    private static int serverPort;
    private final String testFilename;
    private RosetteAPI api;
    private String responseStr;
    private ClientAndServer mockServer;

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public RosetteAPITest(String filename) {
        testFilename = filename;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException {
        URL url = RosetteAPITest.class.getClassLoader().getResource("response");
        File dir = new File(url.toURI());
        File[] files = dir.listFiles();
        Collection<Object[]> params = new ArrayList<Object[]>();
        for (File file : files) {
            System.out.println(file.getName());
            params.add(new Object[]{file.getName()});
        }
        return params;
    }

    @BeforeClass
    public static void before() throws IOException {
        ServerSocket s = new ServerSocket(0);
        serverPort = s.getLocalPort();
        serverPort = 8118;
    }

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = RosetteAPITest.class.getClassLoader().getResourceAsStream("response/" + testFilename);
        responseStr = getStringFromInputStream(inputStream);

        mockServer = startClientAndServer(serverPort);
        mockServer.when(request().withPath("/.*"))
                .respond(response().withStatusCode(200).withBody(responseStr));

        String mockServiceUrl = "http://localhost:" + serverPort + "/rest/v1";
        api = new RosetteAPI();
        api.setServiceUrl(mockServiceUrl);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop();
    }

    @Test
    public void testRosetteAPI() throws Exception {
    }

    @Test
    public void testSetServiceUrl() throws Exception {
    }

    @Test
    public void testSetAPIKey() throws Exception {

    }

    @Test
    public void testMatchName() throws Exception {

    }

    @Test
    public void testTranslateName() throws Exception {

    }

    private RosetteAPIRequest readValue() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("request/" + testFilename);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, RosetteAPIRequest.class);
    }

    private void verifyLanguage(LanguageResponse response) {
        assertTrue(response.getLanguageDetections().size() > 0);
    }

    @Test
    public void testGetLanguageDoc() throws Exception {
        if (!(testFilename.contains("language") && testFilename.contains("-doc-"))) {
            return;
        }
        RosetteAPIRequest request = readValue();
        LanguageResponse response = api.getLanguage(request.getContent());
        verifyLanguage(response);
    }

    @Test
    public void testGetLanguageURL() throws Exception {
        if (!(testFilename.contains("language") && testFilename.contains("-url-"))) {
            return;
        }
        RosetteAPIRequest request = readValue();
        LanguageResponse response = api.getLanguage(new URL(request.getContentUri()));
        verifyLanguage(response);
    }

    @Test
    public void testGetLanguageSentence() throws Exception {
        if (!(testFilename.contains("language") && testFilename.contains("-sentence-"))) {
            return;
        }
        RosetteAPIRequest request = readValue();
        LanguageResponse response = api.getLanguage(request.getContent(), InputUnit.SENTENCE);
        verifyLanguage(response);
    }

    @Test
    public void testGetMorphology() throws Exception {

    }

    @Test
    public void testGetMorphology1() throws Exception {

    }

    @Test
    public void testGetMorphology2() throws Exception {

    }

    @Test
    public void testGetEntity() throws Exception {

    }

    @Test
    public void testGetEntity1() throws Exception {

    }

    @Test
    public void testGetEntity2() throws Exception {

    }

    @Test
    public void testGetLinkedEntity() throws Exception {

    }

    @Test
    public void testGetLinkedEntity1() throws Exception {

    }

    @Test
    public void testGetLinkedEntity2() throws Exception {

    }

    @Test
    public void testGetCategories() throws Exception {

    }

    @Test
    public void testGetCategories1() throws Exception {

    }

    @Test
    public void testGetCategories2() throws Exception {

    }

    @Test
    public void testGetSentiment() throws Exception {

    }

    @Test
    public void testGetSentiment1() throws Exception {

    }

    @Test
    public void testGetSentiment2() throws Exception {

    }
}