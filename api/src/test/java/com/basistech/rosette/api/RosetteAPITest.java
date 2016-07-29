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

package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.*;
import com.basistech.util.LanguageCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RosetteAPITest extends AbstractTest {
    private final String testFilename;
    private RosetteAPI api;
    private String responseStr;
    private LanguageCode language;
    private MockServerClient mockServer;

    public RosetteAPITest(String filename) {
        testFilename = filename;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        File dir = new File("src/test/mock-data/response");
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


    @Before
    public void setUp() throws IOException, InterruptedException, RosetteAPIException {
        try {
            language = LanguageCode.lookupByISO639(testFilename.substring(0, 3));
        } catch (IllegalArgumentException e) {
            language = LanguageCode.UNKNOWN;
        }

        String statusFilename = testFilename.replace(".json", ".status");
        try (InputStream bodyStream = new FileInputStream("src/test/mock-data/response/" + testFilename)) {
            responseStr = IOUtils.toString(bodyStream, "UTF-8");
            int statusCode = 200;

            File statusFile = new File("src/test/mock-data/response", statusFilename);
            if (statusFile.exists()) {
                String statusStr = FileUtils.readFileToString(statusFile, "UTF-8").trim();
                statusCode = Integer.parseInt(statusStr);
            }
            mockServer = new MockServerClient("localhost", serverPort);
            mockServer.reset()
                    .when(HttpRequest.request().withPath(".*/{2,}.*"))
                    .respond(HttpResponse.response()
                                    .withBody("Invalid path; '//'")
                                    .withHeader("X-RosetteApi-Concurrency", "5")
                                    .withStatusCode(404)
                    );
            mockServer.when(HttpRequest.request().withPath("^(?!//).+"), Times.exactly(1)).respond(HttpResponse.response()
                    .withHeader("Content-Type", "application/json")
                    .withHeader("X-RosetteApi-Concurrency", "1")
                    .withBody(INFO_REPONSE, StandardCharsets.UTF_8)
                    .withStatusCode(200));
            if (responseStr.length() > 200) {  // test gzip if response is somewhat big
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Content-Encoding", "gzip")
                                .withHeader("X-RosetteApi-Concurrency", "5")
                                .withStatusCode(statusCode).withBody(gzip(responseStr)));

            } else {
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("X-RosetteApi-Concurrency", "5")
                                .withStatusCode(statusCode).withBody(responseStr, StandardCharsets.UTF_8));
            }
            mockServer.when(HttpRequest.request()
                    .withPath("/info"))
                .respond(HttpResponse.response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withHeader("X-RosetteApi-Concurrency", "5")
                    .withBody(INFO_REPONSE, StandardCharsets.UTF_8));

            String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest/v1";
            api = new RosetteAPI.Builder()
                    .apiKey("my-key-123")
                    .alternateUrl(mockServiceUrl)
                    .build();
        }
    }

    @Test
    public void testMatchName() throws IOException {
        if (!(testFilename.endsWith("-matched-name.json"))) {
            return;
        }
        NameSimilarityRequest request = readValueNameMatcher();
        try {
            NameSimilarityResponse response = api.getNameSimilarity(request);
            verifyNameMatcher(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameMatcher(NameSimilarityResponse response) throws IOException {
        NameSimilarityResponse goldResponse = mapper.readValue(responseStr, NameSimilarityResponse.class);
        assertEquals(goldResponse.getScore(),response.getScore(), 0.0);
    }

    private NameSimilarityRequest readValueNameMatcher() throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameSimilarityRequest.class);
    }

    @Test
    public void testTranslateName() throws IOException {
        if (!(testFilename.endsWith("-translated-name.json"))) {
            return;
        }
        NameTranslationRequest request = readValueNameTranslation();
        try {
            NameTranslationResponse response = api.getNameTranslation(request);
            verifyNameTranslation(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameTranslation(NameTranslationResponse response) throws IOException {
        NameTranslationResponse goldResponse = mapper.readValue(responseStr, NameTranslationResponse.class);
        assertEquals(goldResponse.getTranslation(), response.getTranslation());
    }

    private NameTranslationRequest readValueNameTranslation() throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameTranslationRequest.class);
    }

    private void verifyLanguage(LanguageResponse response) throws IOException {
        LanguageResponse goldResponse = mapper.readValue(responseStr, LanguageResponse.class);
        assertEquals(goldResponse.getLanguageDetections().size(), response.getLanguageDetections().size());
    }

    @Test
    public void testGetLanguageDoc() throws IOException {
        if (!(testFilename.endsWith("-language.json") && testFilename.contains("-doc-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            LanguageResponse response = api.getLanguage(request.getContent());
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            LanguageResponse response = api.getLanguage(new URL(request.getContentUri()));
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(MorphologicalFeature.COMPLETE, request.getContent());
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            MorphologyResponse response = api.getMorphology(MorphologicalFeature.COMPLETE, new URL(request.getContentUri()));
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            EntitiesResponse response = api.getEntities(request.getContent());
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            EntitiesResponse response = api.getEntities(new URL(request.getContentUri()));
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
         DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(request.getContent(), null);
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(new URL(request.getContentUri()), null);
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            CategoriesResponse response = api.getCategories(request.getContent());
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
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            CategoriesResponse response = api.getCategories(new URL(request.getContentUri()));
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetRelationships() throws IOException {
        if (!(testFilename.endsWith("-relationships.json") && testFilename.contains("-doc-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest/v1";
            RosetteAPI apiWithLanguage = new RosetteAPI.Builder()
                                            .apiKey("my-key-123")
                                            .alternateUrl(mockServiceUrl)
                                            .language(language)
                                            .build();
            RelationshipsResponse response = apiWithLanguage.getRelationships(request.getContent());
            verifyRelationships(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyRelationships(RelationshipsResponse response) throws IOException {
        RelationshipsResponse goldResponse = mapper.readValue(responseStr, RelationshipsResponse.class);
        assertEquals(response.getRelationships().size(), goldResponse.getRelationships().size());
    }

    @Test
    public void testGetRelationshipsURL() throws IOException {
        if (!(testFilename.endsWith("-relationships.json") && testFilename.contains("-url-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest/v1";
            RosetteAPI apiWithLanguage = new RosetteAPI.Builder()
                    .apiKey("my-key-123")
                    .alternateUrl(mockServiceUrl)
                    .language(language)
                    .build();
            RelationshipsResponse response = apiWithLanguage.getRelationships(new URL(request.getContentUri()));
            verifyRelationships(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSentiment() throws IOException {
        if (!(testFilename.endsWith("-sentiment.json") && testFilename.contains("-doc-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            SentimentResponse response = api.getSentiment(request.getContent());
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifySentiment(SentimentResponse response) throws IOException {
        SentimentResponse goldResponse = mapper.readValue(responseStr, SentimentResponse.class);
        // this is minimal.
        assertNotNull(response.getEntities());
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    @Test
    public void testGetSentimentURL() throws IOException {
        if (!(testFilename.endsWith("-sentiment.json") && testFilename.contains("-url-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            SentimentResponse response = api.getSentiment(new URL(request.getContentUri()));
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private <T extends Request> T readValue(Class<T> clazz) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, clazz);
    }

    private void verifyException(RosetteAPIException e) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(goldResponse.getCode(), e.getCode());
    }
}
