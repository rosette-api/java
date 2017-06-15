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

import com.basistech.rosette.RosetteRuntimeException;
import com.basistech.rosette.api.common.AbstractRosetteAPI;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameDeduplicationResponse;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.SyntaxDependenciesResponse;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RosetteAPITest extends AbstractTest {
    private final String testFilename;
    private HttpRosetteAPI api;
    private String responseStr;
    private MockServerClient mockServer;
    private String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest/v1";


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
    public void setUp() throws Exception {
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
            mockServer.reset();
            mockServer.when(HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/rest/v1/ping")
                    .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                    .respond(HttpResponse.response()
                            .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}", StandardCharsets.UTF_8)
                            .withStatusCode(200)
                            .withHeader("X-RosetteAPI-Concurrency", "5"));
            if (responseStr.length() > 200) {  // test gzip if response is somewhat big
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Content-Encoding", "gzip")
                                .withHeader("X-RosetteAPI-Concurrency", "5")
                                .withStatusCode(statusCode).withBody(gzip(responseStr)));

            } else {
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("X-RosetteAPI-Concurrency", "5")
                                .withStatusCode(statusCode).withBody(responseStr, StandardCharsets.UTF_8));
            }
            mockServer.when(HttpRequest.request()
                    .withPath("/info"))
                .respond(HttpResponse.response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withHeader("X-RosetteAPI-Concurrency", "5")
                    .withBody(INFO_REPONSE, StandardCharsets.UTF_8));

            api = new HttpRosetteAPI.Builder()
                    .key("my-key-123")
                    .url(mockServiceUrl)
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
            NameSimilarityResponse response = api.perform(AbstractRosetteAPI.NAME_SIMILARITY_SERVICE_PATH, request, NameSimilarityResponse.class);
            verifyNameMatcher(response);
        } catch (HttpRosetteAPIException e) {
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
            NameTranslationResponse response = api.perform(AbstractRosetteAPI.NAME_TRANSLATION_SERVICE_PATH, request, NameTranslationResponse.class);
            verifyNameTranslation(response);
        } catch (HttpRosetteAPIException e) {
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
            LanguageResponse response = api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, request, LanguageResponse.class);
            verifyLanguage(response);
        } catch (HttpRosetteAPIException e) {
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
            LanguageResponse response = api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, request, LanguageResponse.class);
            verifyLanguage(response);
        } catch (HttpRosetteAPIException e) {
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
            MorphologyResponse response = api.perform(AbstractRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/" + MorphologicalFeature.COMPLETE, request, MorphologyResponse.class);
            verifyMorphology(response);
        } catch (HttpRosetteAPIException e) {
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
            MorphologyResponse response = api.perform(AbstractRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/" + MorphologicalFeature.COMPLETE, request, MorphologyResponse.class);
            verifyMorphology(response);
        } catch (HttpRosetteAPIException e) {
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
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
            verifyEntity(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testIgnoredUnknownField() throws IOException {
        if ("unknown-field-entities.json".equals(testFilename)) {
            DocumentRequest<?> request = readValue(DocumentRequest.class);
            try {
                EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
                verifyEntity(response);
            } catch (HttpRosetteAPIException e) {
                verifyException(e);
            }
        }
    }

    @Test
    public void testNonIgnoredUnknownField() throws IOException {
        if ("unknown-field-entities.json".equals(testFilename)) {
            DocumentRequest<?> request = readValue(DocumentRequest.class);
            HttpRosetteAPI tmpApi = new HttpRosetteAPI.Builder()
                    .key("my-key-123")
                    .url(mockServiceUrl)
                    .onlyAcceptKnownFields(true)
                    .build();
            try {
                tmpApi.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
            } catch (RosetteRuntimeException e) {
                if (e.getCause() instanceof UnrecognizedPropertyException) {
                    return;
                }
            }
            fail("Unknown field is ignored when it shouldn't be ");
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
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
            verifyEntity(response);
        } catch (HttpRosetteAPIException e) {
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
            CategoriesResponse response = api.perform(AbstractRosetteAPI.CATEGORIES_SERVICE_PATH, request, CategoriesResponse.class);
            verifyCategory(response);
        } catch (HttpRosetteAPIException e) {
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
            CategoriesResponse response = api.perform(AbstractRosetteAPI.CATEGORIES_SERVICE_PATH, request, CategoriesResponse.class);
            verifyCategory(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSyntaxDependencies() throws IOException {
        if (!(testFilename.endsWith("-syntax_dependencies.json") && testFilename.contains("-doc-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            SyntaxDependenciesResponse response = api.perform(AbstractRosetteAPI.SYNTAX_DEPENDENCIES_SERVICE_PATH, request, SyntaxDependenciesResponse.class);
            verifySyntaxDependency(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }


    private void verifySyntaxDependency(SyntaxDependenciesResponse response) throws IOException {
        SyntaxDependenciesResponse goldResponse = mapper.readValue(responseStr, SyntaxDependenciesResponse.class);
        assertEquals(response.getSentences().size(), goldResponse.getSentences().size());
    }

    @Test
    public void testGetSyntaxDependenciesURL() throws IOException {
        if (!(testFilename.endsWith("-syntax_dependencies.json") && testFilename.contains("-url-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            SyntaxDependenciesResponse response = api.perform(AbstractRosetteAPI.SYNTAX_DEPENDENCIES_SERVICE_PATH, request, SyntaxDependenciesResponse.class);
            verifySyntaxDependency(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }

    // THERE ARE NO REL FILENAMES!
    @Test
    public void testGetRelationships() throws IOException {
        if (!(testFilename.endsWith("-relationships.json") && testFilename.contains("-doc-"))) {
            return;
        }
        DocumentRequest<?> request = readValue(DocumentRequest.class);
        try {
            RelationshipsResponse response = api.perform(AbstractRosetteAPI.RELATIONSHIPS_SERVICE_PATH, request, RelationshipsResponse.class);
            verifyRelationships(response);
        } catch (HttpRosetteAPIException e) {
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
            RelationshipsResponse response = api.perform(AbstractRosetteAPI.RELATIONSHIPS_SERVICE_PATH, request, RelationshipsResponse.class);
            verifyRelationships(response);
        } catch (HttpRosetteAPIException e) {
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
            SentimentResponse response = api.perform(AbstractRosetteAPI.SENTENCES_SERVICE_PATH, request, SentimentResponse.class);
            verifySentiment(response);
        } catch (HttpRosetteAPIException e) {
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
            SentimentResponse response = api.perform(AbstractRosetteAPI.SENTENCES_SERVICE_PATH, request, SentimentResponse.class);
            verifySentiment(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }

    private <T extends Request> T readValue(Class<T> clazz) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, clazz);
    }

    private void verifyException(HttpRosetteAPIException e) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(goldResponse.getCode(), e.getErrorResponse().getCode());
    }

    @Test
    public void testNameDeduplication() throws IOException {
        if (!(testFilename.endsWith("-name-deduplication.json"))) {
            return;
        }
        NameDeduplicationRequest request = readValueNameDeduplication();
        try {
            NameDeduplicationResponse response = api.perform(AbstractRosetteAPI.NAME_DEDUPLICATION_SERVICE_PATH,
                    request, NameDeduplicationResponse.class);
            verifyNameDeduplication(response);
        } catch (HttpRosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyNameDeduplication(NameDeduplicationResponse response) throws IOException {
        NameDeduplicationResponse goldResponse = mapper.readValue(responseStr, NameDeduplicationResponse.class);
        assertEquals(goldResponse.getResults(),response.getResults());
    }

    private NameDeduplicationRequest readValueNameDeduplication() throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameDeduplicationRequest.class);
    }
}
