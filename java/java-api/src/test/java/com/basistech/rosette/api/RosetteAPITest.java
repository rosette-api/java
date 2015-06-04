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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import com.basistech.rosette.apimodel.CategoryRequest;
import com.basistech.rosette.apimodel.CategoryResponse;
import com.basistech.rosette.apimodel.EntityRequest;
import com.basistech.rosette.apimodel.EntityResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LinguisticsRequest;
import com.basistech.rosette.apimodel.LinkedEntityRequest;
import com.basistech.rosette.apimodel.LinkedEntityResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameMatcherRequest;
import com.basistech.rosette.apimodel.NameMatcherResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Parameterized.class)
public class RosetteAPITest extends Assert {

    private static int serverPort;
    private static ObjectMapper mapper;
    private final String testFilename;
    private RosetteAPI api;
    private String responseStr;
    private ClientAndServer mockServer;
    private String language;

    public RosetteAPITest(String filename) {
        testFilename = filename;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        URL url = RosetteAPITest.class.getClassLoader().getResource("response");
        File dir = new File(url.toURI());
        Collection<Object[]> params = new ArrayList<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir.toPath())) {
            for (Path file : paths) {
                if (file.toString().endsWith(".json")) {
                    params.add(new Object[]{file.getFileName().toString()});
                }
            }
        }
        return params;
    }

    @BeforeClass
    public static void before() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            serverPort = s.getLocalPort();
        }
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        language = testFilename.substring(0, 3);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("response/" + testFilename);
        responseStr = getStringFromInputStream(inputStream);

        String statusFilename = testFilename.replace(".json", ".status");
        inputStream = RosetteAPITest.class.getClassLoader().getResourceAsStream("response/" + statusFilename);
        int statusCode = 200;
        if (inputStream != null) {
            String statusStr = getStringFromInputStream(inputStream);
            statusCode = Integer.parseInt(statusStr);
        }

        mockServer = ClientAndServer.startClientAndServer(serverPort);
        mockServer.when(HttpRequest.request().withPath("/.*"))
                .respond(HttpResponse.response().withStatusCode(statusCode).withBody(responseStr));

        String mockServiceUrl = "http://localhost:" + serverPort + "/rest/v1";
        api = new RosetteAPI();
        api.setUrlBase(mockServiceUrl);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop();
    }

    @Test
    public void testMatchName() throws IOException {
        if (!(testFilename.endsWith("-matched-name.json"))) {
            return;
        }
        NameMatcherRequest request = readValueNameMatcher();
        try {
            NameMatcherResponse response = api.matchName(request);
            verifyNameMatcher(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameMatcher(NameMatcherResponse response) throws IOException {
        NameMatcherResponse goldResponse = mapper.readValue(responseStr, NameMatcherResponse.class);
        assertEquals(response.getResult().getScore(), goldResponse.getResult().getScore(), 0.0);
    }

    private NameMatcherRequest readValueNameMatcher() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("request/" + testFilename);
        return mapper.readValue(inputStream, NameMatcherRequest.class);
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("request/" + testFilename);
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
        Request request = readValue(LinguisticsRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE, request.getContent(), null, null);
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
        Request request = readValue(LinguisticsRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE, new URL(request.getContentUri()), null, null);
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
        Request request = readValue(LinguisticsRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE, request.getContent(), null, InputUnit.sentence, null);
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
        Request request = readValue(EntityRequest.class);
        try {
            EntityResponse response = api.getEntity(request.getContent(), null, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyEntity(EntityResponse response) throws IOException {
        EntityResponse goldResponse = mapper.readValue(responseStr, EntityResponse.class);
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    @Test
    public void testGetEntityURL() throws IOException {
        if (!(testFilename.endsWith("-entities.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(EntityRequest.class);
        try {
            EntityResponse response = api.getEntity(new URL(request.getContentUri()), null, null);
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
        Request request = readValue(EntityRequest.class);
        try {
            EntityResponse response = api.getEntity(request.getContent(), null, InputUnit.sentence, null);
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
        Request request = readValue(LinkedEntityRequest.class);
        try {
            LinkedEntityResponse response = api.getLinkedEntity(request.getContent(), null, null);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyLinkedEntity(LinkedEntityResponse response) throws IOException {
        LinkedEntityResponse goldResponse = mapper.readValue(responseStr, LinkedEntityResponse.class);
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    @Test
    public void testGetLinkedEntityURL() throws IOException {
        if (!(testFilename.endsWith("-entities_linked.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(LinkedEntityRequest.class);
        try {
            LinkedEntityResponse response = api.getLinkedEntity(new URL(request.getContentUri()), null);
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
        Request request = readValue(LinkedEntityRequest.class);
        try {
            LinkedEntityResponse response = api.getLinkedEntity(request.getContent(), null, InputUnit.sentence);
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
        Request request = readValue(CategoryRequest.class);
        try {
            CategoryResponse response = api.getCategories(request.getContent(), null, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }


    private void verifyCategory(CategoryResponse response) throws IOException {
        CategoryResponse goldResponse = mapper.readValue(responseStr, CategoryResponse.class);
        assertEquals(response.getCategories().size(), goldResponse.getCategories().size());
    }

    @Test
    public void testGetCategoriesURL() throws IOException {
        if (!(testFilename.endsWith("-categories.json") && testFilename.contains("-url-"))) {
            return;
        }
        Request request = readValue(CategoryRequest.class);
        try {
            CategoryResponse response = api.getCategories(new URL(request.getContentUri()), null, null);
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
        Request request = readValue(CategoryRequest.class);
        try {
            CategoryResponse response = api.getCategories(request.getContent(), null, InputUnit.sentence, null);
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("request/" + testFilename);
        return mapper.readValue(inputStream, clazz);
    }

    private void verifyException(RosetteAPIException e) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(e.getCode(), goldResponse.getCode());
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
