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

import com.basistech.rosette.apimodel.CategoriesRequest;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.EntitiesRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LinkedEntitiesRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.RelationshipsRequest;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.SentencesRequest;
import com.basistech.rosette.apimodel.SentencesResponse;
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

        try (InputStream bodyStream = new FileInputStream("src/test/mock-data/response/doc-data.json")) {
            responseStr = IOUtils.toString(bodyStream, "UTF-8");
            int statusCode = 200;

            mockServer = new MockServerClient("localhost", serverPort);
            mockServer.reset()
                    .when(HttpRequest.request().withPath(".*/{2,}.*"))
                    .respond(HttpResponse.response()
                                    .withBody("Invalid path; '//'")
                                    .withStatusCode(404)
                    );
            mockServer.when(HttpRequest.request().withPath("^(?!//).+"), Times.exactly(1)).respond(HttpResponse.response()
                    .withHeader("Content-Type", "application/json")
                    .withBody(INFO_REPONSE, StandardCharsets.UTF_8)
                    .withStatusCode(200));
            if (responseStr.length() > 200) {  // test gzip if response is somewhat big
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Content-Encoding", "gzip")
                                .withStatusCode(statusCode).withBody(gzip(responseStr)));
            } else {
                mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                        .respond(HttpResponse.response()
                                .withHeader("Content-Type", "application/json")
                                .withStatusCode(statusCode).withBody(responseStr, StandardCharsets.UTF_8));
            }
            mockServer.when(HttpRequest.request()
                    .withPath("/info"))
                .respond(HttpResponse.response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(INFO_REPONSE, StandardCharsets.UTF_8));

            String mockServiceUrl = "http://localhost:" + Integer.toString(serverPort) + "/rest/v1";
            api = new RosetteAPI("my-key-123", mockServiceUrl);
        }
    }

    @Test
    public void testMatchName() throws IOException {
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
        assertEquals(goldResponse,response);
    }

    private NameSimilarityRequest readValueNameMatcher() throws IOException {
        return mapper.readValue("{\"name1\": {\"entityType\": \"PERSON\", \"language\": \"eng\", \"script\": \"Latn\", \"text\": \"John Doe\"}, \"name2\": {\"entityType\": \"PERSON\", \"language\": \"fra\", \"script\": \"Latn\", \"text\": \"Jane Doe\"}}", NameSimilarityRequest.class);
    }

    @Test
    public void testTranslateName() throws IOException {
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
        return mapper.readValue("{\"entityType\": \"PERSON\", \"name\": \"毛泽东\", \"sourceLanguageOfOrigin\": \"zho\", \"sourceLanguageOfUse\": \"zho\", \"sourceScript\": \"Hani\", \"targetLanguage\": \"eng\", \"targetScheme\": \"HYPY_TONED\", \"targetScript\": \"Latn\"}", NameTranslationRequest.class);
    }

    private void verifyLanguage(LanguageResponse response) throws IOException {
        LanguageResponse goldResponse = mapper.readValue(responseStr, LanguageResponse.class);
        assertEquals(goldResponse, response);
    }

    @Test
    public void testGetLanguageDoc() throws IOException {
        try {
            LanguageResponse response = api.getLanguage("sample request", null);
            verifyLanguage(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetLanguageURL() throws IOException {
        try {
            LanguageResponse response = api.getLanguage(new URL("http://example.com"), null);
            verifyLanguage(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetMorphologyDoc() throws IOException {
        try {
            MorphologyResponse response = api.getMorphology(MorphologicalFeature.COMPLETE,
                    "sample request", null, null);
            verifyMorphology(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyMorphology(MorphologyResponse response) throws IOException {
        MorphologyResponse goldResponse = mapper.readValue(responseStr, MorphologyResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetMorphologyURL() throws IOException {
        try {
            MorphologyResponse response = api.getMorphology(MorphologicalFeature.COMPLETE,
                    new URL("http://example.com"), null, null);
            verifyMorphology(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetEntityDoc() throws IOException {
        try {
            EntitiesResponse response = api.getEntities("sample request", null, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyEntity(EntitiesResponse response) throws IOException {
        EntitiesResponse goldResponse = mapper.readValue(responseStr, EntitiesResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetEntityURL() throws IOException {
        try {
            EntitiesResponse response = api.getEntities(new URL("http://example.com"), null, null);
            verifyEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

     @Test
    public void testGetLinkedEntity() throws IOException {
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities("sample request", null);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyLinkedEntity(LinkedEntitiesResponse response) throws IOException {
        LinkedEntitiesResponse goldResponse = mapper.readValue(responseStr, LinkedEntitiesResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetLinkedEntityURL() throws IOException {
        try {
            LinkedEntitiesResponse response = api.getLinkedEntities(new URL("http://example.com"), null);
            verifyLinkedEntity(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetCategories() throws IOException {
        try {
            CategoriesResponse response = api.getCategories("sample request", null, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }


    private void verifyCategory(CategoriesResponse response) throws IOException {
        CategoriesResponse goldResponse = mapper.readValue(responseStr, CategoriesResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetCategoriesURL() throws IOException {
        try {
            CategoriesResponse response = api.getCategories(new URL("http://example.com"), null, null);
            verifyCategory(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetRelationships() throws IOException {
        try {
            RelationshipsResponse response = api.getRelationships("sample request", language, null);
            verifyRelationships(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyRelationships(RelationshipsResponse response) throws IOException {
        RelationshipsResponse goldResponse = mapper.readValue(responseStr, RelationshipsResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetRelationshipsURL() throws IOException {
        try {
            RelationshipsResponse response = api.getRelationships(new URL("http://example.com"), language, null);
            verifyRelationships(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSentiment() throws IOException {
        try {
            SentimentResponse response = api.getSentiment("sample content", language, null);
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifySentiment(SentimentResponse response) throws IOException {
        SentimentResponse goldResponse = mapper.readValue(responseStr, SentimentResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetSentimentURL() throws IOException {
        try {
            SentimentResponse response = api.getSentiment(new URL("http://example.com"), language, null);
            verifySentiment(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetTokens() throws IOException {
        try {
            TokensResponse response = api.getTokens("sample content", null);
            verifyTokens(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyTokens(TokensResponse response) throws IOException {
        TokensResponse goldResponse = mapper.readValue(responseStr, TokensResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetTokensURL() throws IOException {
        try {
            TokensResponse response = api.getTokens(new URL("http://example.com"), null);
            verifyTokens(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    @Test
    public void testGetSentences() throws IOException {
        try {
            SentencesResponse response = api.getSentences("sample content", null);
            verifySentences(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifySentences(SentencesResponse response) throws IOException {
        SentencesResponse goldResponse = mapper.readValue(responseStr, SentencesResponse.class);
        assertEquals(response, goldResponse);
    }

    @Test
    public void testGetSentencesURL() throws IOException {
        try {
            SentencesResponse response = api.getSentences(new URL("http://example.com"), null);
            verifySentences(response);
        } catch (RosetteAPIException e) {
            verifyException(e);
        }
    }

    private void verifyException(RosetteAPIException e) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(goldResponse.getCode(), e.getCode());
    }


}