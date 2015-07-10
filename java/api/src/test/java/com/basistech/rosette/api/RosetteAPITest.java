/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basistech.rosette.apimodel.CategoriesRequest;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.EntitiesRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.LanguageCode;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameMatchingRequest;
import com.basistech.rosette.apimodel.NameMatchingResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;

@RunWith(Parameterized.class)
public class RosetteAPITest extends Assert {

    private int serverPort;
    private static ObjectMapper mapper;
    private final String testFilename;
    private RosetteAPI api;
    private String responseStr;
    private LanguageCode language;

    public RosetteAPITest(int serverPort, String filename) {
        this.serverPort = serverPort;
        testFilename = filename;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        int serverPort;
        try (
            InputStream is = RosetteAPITest.class.getClassLoader().getResourceAsStream("MockServerClientPort.property")
        ) {
            String s = getStringFromInputStream(is);
            serverPort = Integer.parseInt(s);
        }
        URL url = RosetteAPITest.class.getClassLoader().getResource("mock-data/response");
        File dir = new File(url.toURI());
        Collection<Object[]> params = new ArrayList<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir.toPath())) {
            for (Path file : paths) {
                if (file.toString().endsWith(".json")) {
                    params.add(new Object[]{serverPort, file.getFileName().toString()});
                }
            }
        }
        return params;
    }

    @BeforeClass
    public static void before() throws IOException {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        try {
            language = LanguageCode.valueOf(testFilename.substring(0, 3));
        } catch (IllegalArgumentException e) {
            language = LanguageCode.xxx;
        }

        String statusFilename = testFilename.replace(".json", ".status");
        try (InputStream bodyStream = getClass().getClassLoader().getResourceAsStream(
                     "mock-data/response/" + testFilename);
             InputStream statusStream = getClass().getClassLoader().getResourceAsStream(
                     "mock-data/response/" + statusFilename)) {
            responseStr = getStringFromInputStream(bodyStream);
            int statusCode = 200;
            if (statusStream != null) {
                String statusStr = getStringFromInputStream(statusStream);
                statusCode = Integer.parseInt(statusStr);
            }
            if (responseStr.length() > 200) {  // test gzip if response is somewhat big
                new MockServerClient("localhost", serverPort)
                        .reset()
                        .when(HttpRequest.request().withPath("/.*"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Content-Encoding", "gzip")
                                .withStatusCode(statusCode).withBody(gzip(responseStr)));
            } else {
                new MockServerClient("localhost", serverPort)
                        .reset()
                        .when(HttpRequest.request().withPath("/.*"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withStatusCode(statusCode).withBody(responseStr, StandardCharsets.UTF_8));
            }

            String mockServiceUrl = "http://localhost:" + serverPort + "/rest/v1";
            api = new RosetteAPI();
            api.setUrlBase(mockServiceUrl);
        }
    }

    @Test
    public void testMatchName() throws IOException {
        if (!(testFilename.endsWith("-matched-name.json"))) {
            return;
        }
        NameMatchingRequest request = readValueNameMatcher();
        try {
            NameMatchingResponse response = api.matchName(request);
            verifyNameMatcher(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameMatcher(NameMatchingResponse response) throws IOException {
        NameMatchingResponse goldResponse = mapper.readValue(responseStr, NameMatchingResponse.class);
        assertEquals(response.getResult().getScore(), goldResponse.getResult().getScore(), 0.0);
    }

    private NameMatchingRequest readValueNameMatcher() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-data/request/" + testFilename);
        return mapper.readValue(inputStream, NameMatchingRequest.class);
    }

    @Test
    public void testTranslateName() throws IOException {
        if (!(testFilename.endsWith("-translated-name.json"))) {
            return;
        }
        NameTranslationRequest request = readValueNameTranslation();
        try {
            NameTranslationResponse response = api.translateName(request);
            verifyNameTranslation(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameTranslation(NameTranslationResponse response) throws IOException {
        NameTranslationResponse goldResponse = mapper.readValue(responseStr, NameTranslationResponse.class);
        assertEquals(response.getResult().getTranslation(), goldResponse.getResult().getTranslation());
    }

    private NameTranslationRequest readValueNameTranslation() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-data/request/" + testFilename);
        return mapper.readValue(inputStream, NameTranslationRequest.class);
    }


    private void verifyLanguage(LanguageResponse response) throws IOException {
        LanguageResponse goldResponse = mapper.readValue(responseStr, LanguageResponse.class);
        assertEquals(response.getLanguageDetections().size(), goldResponse.getLanguageDetections().size());
    }

    @Test
    public void testGetLanguageDoc() throws IOException {
        if (!(testFilename.endsWith("-language.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(LanguageRequest.class);
        try {
            LanguageResponse response = api.getLanguage(request.getContent(), null);
            verifyLanguage(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetLanguageURL() throws IOException {
        if (!(testFilename.endsWith("-language.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(LanguageRequest.class);
        try {
            LanguageResponse response = api.getLanguage(new URL(request.getContentUri()), null);
            verifyLanguage(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetLanguageSentence() throws IOException {
        if (!(testFilename.contains("-language.json") && testFilename.contains("-sentence-"))) {
            return;
        }
        Request request = readValue(LanguageRequest.class);
        try {
            LanguageResponse response = api.getLanguage(request.getContent(), InputUnit.sentence, null);
            verifyLanguage(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetMorphologyDoc() throws IOException {
        if (!(testFilename.endsWith("-morphology_complete.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(MorphologyRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE,
                    request.getContent(), null, null);
            verifyMorphology(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyMorphology(MorphologyResponse response) throws IOException {
        MorphologyResponse goldResponse = mapper.readValue(responseStr, MorphologyResponse.class);
        assertEquals(response.getPosTags().size(), goldResponse.getPosTags().size());
    }

    @Test
    public void testGetMorphologyURL() throws IOException {
        if (!(testFilename.endsWith("-morphology_complete.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(MorphologyRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE,
                    new URL(request.getContentUri()), null, null);
            verifyMorphology(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetMorphologySentence() throws IOException {
        if (!(testFilename.endsWith("-morphology_complete.json") && testFilename.contains("-sentence-"))) {
            return;
        }
        Request request = readValue(MorphologyRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE,
                    request.getContent(), null, InputUnit.sentence, null);
            verifyMorphology(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetEntityDoc() throws IOException {
        if (!(testFilename.endsWith("-entities.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(EntitiesRequest.class);
        try {
            EntitiesResponse response = api.getEntities(request.getContent(), null, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyEntity(EntitiesResponse response) throws IOException {
        EntitiesResponse goldResponse = mapper.readValue(responseStr, EntitiesResponse.class);
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    @Test
    public void testGetEntityURL() throws IOException {
        if (!(testFilename.endsWith("-entities.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(EntitiesRequest.class);
        try {
            EntitiesResponse response = api.getEntities(new URL(request.getContentUri()), null, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetEntitySentence() throws IOException {
        if (!(testFilename.endsWith("-entities.json") && testFilename.contains("-sentence-"))) {
            return;
        }
        Request request = readValue(EntitiesRequest.class);
        try {
            EntitiesResponse response = api.getEntities(request.getContent(), null, InputUnit.sentence, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetLinkedEntity() throws IOException {
        if (!(testFilename.endsWith("-entities_linked.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(LinkedEntitiesRequest.class);
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(request.getContent(), null, null);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyLinkedEntity(LinkedEntitiesResponse response) throws IOException {
        LinkedEntitiesResponse goldResponse = mapper.readValue(responseStr, LinkedEntitiesResponse.class);
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    @Test
    public void testGetLinkedEntityURL() throws IOException {
        if (!(testFilename.endsWith("-entities_linked.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(LinkedEntitiesRequest.class);
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(new URL(request.getContentUri()), null);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetLinkedEntitySentence() throws IOException {
        if (!(testFilename.endsWith("-entities_linked.json") && testFilename.contains("-sentence-"))) {
            return;
        }
        Request request = readValue(LinkedEntitiesRequest.class);
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(request.getContent(), null, InputUnit.sentence);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetCategories() throws IOException {
        if (!(testFilename.endsWith("-categories.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(CategoriesRequest.class);
        try {
            CategoriesResponse response = api.getCategories(request.getContent(), null, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }


    private void verifyCategory(CategoriesResponse response) throws IOException {
        CategoriesResponse goldResponse = mapper.readValue(responseStr, CategoriesResponse.class);
        assertEquals(response.getCategories().size(), goldResponse.getCategories().size());
    }

    @Test
    public void testGetCategoriesURL() throws IOException {
        if (!(testFilename.endsWith("-categories.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(CategoriesRequest.class);
        try {
            CategoriesResponse response = api.getCategories(new URL(request.getContentUri()), null, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetCategoriesSentence() throws IOException {
        if (!(testFilename.endsWith("-categories.json") && testFilename.contains("-sentence-"))) {
            return;
        }
        Request request = readValue(CategoriesRequest.class);
        try {
            CategoriesResponse response = api.getCategories(request.getContent(), null, InputUnit.sentence, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSentiment() throws IOException {
        if (!(testFilename.endsWith("-sentiment.json") && testFilename.contains("-doc-"))) {
            return;
        }
        Request request = readValue(SentimentRequest.class);
        try {
            SentimentResponse response = api.getSentiment(request.getContent(), language, null);
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifySentiment(SentimentResponse response) throws IOException {
        SentimentResponse goldResponse = mapper.readValue(responseStr, SentimentResponse.class);
        assertEquals(response.getSentiment().size(), goldResponse.getSentiment().size());
    }

    @Test
    public void testGetSentimentURL() throws IOException {
        if (!(testFilename.endsWith("-sentiment.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(SentimentRequest.class);
        try {
            SentimentResponse response = api.getSentiment(new URL(request.getContentUri()), language, null);
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSentimentSentence() throws IOException {
        if (!(testFilename.endsWith("-sentiment.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(SentimentRequest.class);
        try {
            SentimentResponse response = api.getSentiment(request.getContent(), language, InputUnit.sentence, null);
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private <T extends Request> T readValue(Class<T> clazz) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-data/request/" + testFilename);
        return mapper.readValue(inputStream, clazz);
    }

    private void verifyException(RosetteAPIException e) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(e.getCode(), goldResponse.getCode());
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.name()))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static byte[] gzip(String text) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(baos)) {
            out.write(text.getBytes(StandardCharsets.UTF_8));
        }
        return baos.toByteArray();
    }
}
