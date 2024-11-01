/*
* Copyright 2024 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.AddressSimilarityRequest;
import com.basistech.rosette.apimodel.AddressSimilarityResponse;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesOptions;
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
import com.basistech.rosette.apimodel.SimilarTermsResponse;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.SyntaxDependenciesResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockServerExtension.class)
@SuppressWarnings("PMD.UnusedPrivateMethod") // Parameterized Tests
class RosetteAPITest {
    private static final String INFO_RESPONSE = "{ \"buildNumber\": \"6bafb29d\", \"buildTime\": "
        + "\"2015.10.08_10:19:26\", \"name\": \"RosetteAPI\", \"version\": \"0.7.0\", \"versionChecked\": true }";
    private HttpRosetteAPI api;
    private MockServerClient mockServer;
    private ObjectMapper mapper;



    @BeforeEach
    public void setUp(MockServerClient mockServer) {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.mockServer = mockServer;
        this.mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/rest/v1/ping")
                .withHeader(HttpHeaders.USER_AGENT, HttpRosetteAPI.USER_AGENT_STR))
                .respond(HttpResponse.response()
                        .withBody("{\"message\":\"Rosette API at your service\",\"time\":1461788498633}",
                                StandardCharsets.UTF_8)
                        .withStatusCode(HTTP_OK)
                        .withHeader("X-BabelStreetAPI-Concurrency", "5"));

        this.mockServer.when(HttpRequest.request()
                .withPath("/info"))
            .respond(HttpResponse.response()
                .withStatusCode(HTTP_OK)
                .withHeader("Content-Type", "application/json")
                .withHeader("X-BabelStreetAPI-Concurrency", "5")
                .withBody(INFO_RESPONSE, StandardCharsets.UTF_8));

        api = new HttpRosetteAPI.Builder()
                .key("my-key-123")
                .url(String.format("http://localhost:%d/rest/v1", mockServer.getPort()))
                .build();
    }

    @AfterEach
    public void reset() {
        mockServer.reset();
    }

    private static byte[] gzip(String text) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(baos)) {
            out.write(text.getBytes(StandardCharsets.UTF_8));
        }
        return baos.toByteArray();
    }

    private <T extends Request> T readValue(Class<T> clazz, String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, clazz);
    }

    private void verifyException(HttpRosetteAPIException e, String responseStr) throws IOException {
        ErrorResponse goldResponse = mapper.readValue(responseStr, ErrorResponse.class);
        assertEquals(goldResponse.getCode(), e.getErrorResponse().getCode());
    }

    private void setStatusCodeResponse(String responseStr, int statusCode) throws IOException {
        if (responseStr.length() > 200) {  // test gzip if response is somewhat big
            mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                    .respond(HttpResponse.response()
                            .withHeader("Content-Type", "application/json")
                            .withHeader("Content-Encoding", "gzip")
                            .withHeader("X-BabelStreetAPI-Concurrency", "5")
                            .withStatusCode(statusCode).withBody(gzip(responseStr)));

        } else {
            mockServer.when(HttpRequest.request().withPath("^(?!/info).+"))
                    .respond(HttpResponse.response()
                            .withHeader("Content-Type", "application/json")
                            .withHeader("X-BabelStreetAPI-Concurrency", "5")
                            .withStatusCode(statusCode).withBody(responseStr, StandardCharsets.UTF_8));
        }

    }

    // Construct the parameters for our tests.  We look inside the mock-data/response directory
    // and gather any matching files.  We read in the response string and response status code.
    // These two values are combined with the name of the file with the request data.  The request
    // data is read in later, from a different directory, when executing the test.
    private static Stream<Arguments> getTestFiles(String endsWith) throws IOException {
        File responseDir = new File("src/test/mock-data/response");
        Stream.Builder<Arguments> streamBuilder = Stream.builder();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(responseDir.toPath())) {
            for (Path p : paths) {
                if (p.toString().endsWith(endsWith)) {
                    var responseBodyStream = new FileInputStream(p.toFile());
                    var responseStr = IOUtils.toString(responseBodyStream, StandardCharsets.UTF_8);
                    responseBodyStream.close();

                    int statusCode = HTTP_OK;
                    var statusFilename = p.toString().replace(".json", ".status");
                    File statusFile = new File(statusFilename);
                    if (statusFile.exists()) {
                        var statusStr = FileUtils.readFileToString(statusFile, StandardCharsets.UTF_8).trim();
                        statusCode = Integer.parseInt(statusStr);
                    }

                    streamBuilder.add(Arguments.of(p.getFileName().toString(), responseStr, statusCode));
                }
            }
        }
        return streamBuilder.build();
    }

    private static Stream<Arguments> testMatchNameParameters() throws IOException {
        return getTestFiles("-name-similarity.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMatchNameParameters")
    void testMatchName(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        NameSimilarityRequest request = readValueNameMatcher(testFilename);
        try {
            NameSimilarityResponse response = api.perform(AbstractRosetteAPI.NAME_SIMILARITY_SERVICE_PATH, request,
                    NameSimilarityResponse.class);
            verifyNameMatcher(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyNameMatcher(NameSimilarityResponse response, String responseStr) throws IOException {
        NameSimilarityResponse goldResponse = mapper.readValue(responseStr, NameSimilarityResponse.class);
        assertEquals(goldResponse.getScore(), response.getScore(), 0.0);
    }

    private NameSimilarityRequest readValueNameMatcher(String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameSimilarityRequest.class);
    }

    private static Stream<Arguments> testMatchRecordParameters() throws IOException {
        return getTestFiles("-record-similarity.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMatchRecordParameters")
    void testMatchRecord(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        RecordSimilarityRequest request = readValueRecordMatcher(testFilename);
        try {
            RecordSimilarityResponse response = api.perform(AbstractRosetteAPI.RECORD_SIMILARITY_SERVICE_PATH, request,
                    RecordSimilarityResponse.class);
            verifyRecordMatcher(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testMatchRecordMissingFieldParameters() throws IOException {
        return getTestFiles("-record-similarity-missing-field.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMatchRecordMissingFieldParameters")
    void testMatchRecordMissingField(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        readValueRecordMatcher(testFilename);
        assertEquals("{\"results\":[{\"score\":0.0,\"left\":{\"dob2\":\"1993/04/16\","
                    + "\"dob\":\"1993-04-16\",\"primaryName\":{\"data\":\"Ethan R\",\"language\":\"eng\","
                    + "\"entityType\":\"PERSON\"},\"addr\":\"123 Roadlane Ave\"},\"right\":{\"dob\":\"1993-04-16\","
                    + "\"primaryName\":\"Seth R\"},\"error\":\"Field 'primaryName' not found in field mapping\"},"
                    + "{\"score\":0.0,\"left\":{\"dob\":\"1993-04-16\",\"primaryName\":\"Evan R\"},"
                    + "\"right\":{\"dob2\":\"1993/04/16\",\"dob\":\"1993-04-16\",\"primaryName\":\"Ivan R\","
                    + "\"addr\":\"123 Roadlane Ave\"},\"error\":\"Field 'primaryName' not found in field mapping\"}]}",
                    responseStr);
    }

    private static Stream<Arguments> testMatchRecordNullFieldParameters() throws IOException {
        return getTestFiles("-record-similarity-null-field.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMatchRecordNullFieldParameters")
    void testMatchRecordNullField(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);

        try {
            readValueRecordMatcher(testFilename);
            fail("Did not throw exception for a field with null type");
        } catch (IllegalArgumentException e) {
            assertEquals("Unspecified field type for: primaryName", e.getMessage());
        }
    }

    private void verifyRecordMatcher(RecordSimilarityResponse response, String responseStr) throws IOException {
        RecordSimilarityResponse goldResponse = mapper.readValue(responseStr, RecordSimilarityResponse.class);
        assertEquals(goldResponse.getResults(), response.getResults());
    }

    private RecordSimilarityRequest readValueRecordMatcher(String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, RecordSimilarityRequest.class);
    }

    private static Stream<Arguments> testMatchAddressParameters() throws IOException {
        return getTestFiles("-address-similarity.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMatchAddressParameters")
    void testMatchAddress(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        AddressSimilarityRequest request = readValueAddressMatcher(testFilename);
        try {
            AddressSimilarityResponse response = api.perform(AbstractRosetteAPI.ADDRESS_SIMILARITY_SERVICE_PATH,
                    request, AddressSimilarityResponse.class);
            verifyAddressMatcher(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyAddressMatcher(AddressSimilarityResponse response, String responseStr) throws IOException {
        AddressSimilarityResponse goldResponse = mapper.readValue(responseStr, AddressSimilarityResponse.class);
        assertEquals(goldResponse.getScore(), response.getScore(), 0.0);
    }

    private AddressSimilarityRequest readValueAddressMatcher(String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, AddressSimilarityRequest.class);
    }

    private static Stream<Arguments> testTranslateNameParameters() throws IOException {
        return getTestFiles("-name-translation.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testTranslateNameParameters")
    void testTranslateName(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        NameTranslationRequest request = readValueNameTranslation(testFilename);
        try {
            NameTranslationResponse response = api.perform(AbstractRosetteAPI.NAME_TRANSLATION_SERVICE_PATH, request,
                    NameTranslationResponse.class);
            verifyNameTranslation(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testMultiTranslateNameParameters() throws IOException {
        return getTestFiles("-multi-name-translation.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testMultiTranslateNameParameters")
    void testMultiTranslateName(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        NameTranslationRequest request = readValueNameTranslation(testFilename);
        try {
            NameTranslationResponse response = api.perform(AbstractRosetteAPI.NAME_TRANSLATION_SERVICE_PATH, request,
                    NameTranslationResponse.class);
            verifyMultiNameTranslations(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyNameTranslation(NameTranslationResponse response, String responseStr) throws IOException {
        NameTranslationResponse goldResponse = mapper.readValue(responseStr, NameTranslationResponse.class);
        assertEquals(goldResponse.getTranslation(), response.getTranslation());
    }

    private void verifyMultiNameTranslations(NameTranslationResponse response, String responseStr) throws IOException {
        NameTranslationResponse goldResponse = mapper.readValue(responseStr, NameTranslationResponse.class);
        assertEquals(goldResponse.getTranslations(), response.getTranslations());
    }

    private NameTranslationRequest readValueNameTranslation(String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameTranslationRequest.class);
    }

    private void verifyLanguage(LanguageResponse response, String responseStr) throws IOException {
        LanguageResponse goldResponse = mapper.readValue(responseStr, LanguageResponse.class);
        assertEquals(goldResponse.getLanguageDetections().size(), response.getLanguageDetections().size());
    }

    private static Stream<Arguments> testGetLanguageDocParameters() throws IOException {
        return getTestFiles("-doc-language.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetLanguageDocParameters")
    void testGetLanguageDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            LanguageResponse response = api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, request,
                    LanguageResponse.class);
            verifyLanguage(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetLanguageURLParameters() throws IOException {
        return getTestFiles("-url-language.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetLanguageURLParameters")
    void testGetLanguageURL(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            LanguageResponse response = api.perform(AbstractRosetteAPI.LANGUAGE_SERVICE_PATH, request,
                    LanguageResponse.class);
            verifyLanguage(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetMorphologyDocParameters() throws IOException {
        return getTestFiles("-doc-morphology_complete.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetMorphologyDocParameters")
    void testGetMorphologyDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            MorphologyResponse response = api.perform(AbstractRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/"
                    + MorphologicalFeature.COMPLETE, request, MorphologyResponse.class);
            verifyMorphology(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyMorphology(MorphologyResponse response, String responseStr) throws IOException {
        MorphologyResponse goldResponse = mapper.readValue(responseStr, MorphologyResponse.class);
        assertEquals(response.getPosTags().size(), goldResponse.getPosTags().size());
    }

    private static Stream<Arguments> testGetMorphologyURLParameters() throws IOException {
        return getTestFiles("-url-morphology_complete.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetMorphologyURLParameters")
    void testGetMorphologyURL(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            MorphologyResponse response = api.perform(AbstractRosetteAPI.MORPHOLOGY_SERVICE_PATH + "/"
                    + MorphologicalFeature.COMPLETE, request, MorphologyResponse.class);
            verifyMorphology(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetEntityDocParameters() throws IOException {
        return getTestFiles("-doc-entities.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetEntityDocParameters")
    void testGetEntityDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request,
                    EntitiesResponse.class);
            verifyEntity(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetEntityLinkedParameters() throws IOException {
        return getTestFiles("-entities_linked.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetEntityLinkedParameters")
    void testGetEntityLinked(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request,
                    EntitiesResponse.class);
            verifyEntity(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }


    private static Stream<Arguments> testGetEntityPermIdParameters() throws IOException {
        return getTestFiles("-entities_permid.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetEntityPermIdParameters")
    void testGetEntityPermId(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        if (testFilename.endsWith("-entities_permid.json")) {
            DocumentRequest<EntitiesOptions> request = readValue(DocumentRequest.class, testFilename);
            try {
                EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request,
                        EntitiesResponse.class);
                verifyEntity(response, responseStr);
            } catch (HttpRosetteAPIException e) {
                verifyException(e, responseStr);
            }
        }
    }

    private static Stream<Arguments> testIgnoredUnknownFieldParameters() throws IOException {
        return getTestFiles("unknown-field-entities.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testIgnoredUnknownFieldParameters")
    void testIgnoredUnknownField(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request,
                    EntitiesResponse.class);
            verifyEntity(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testNonIgnoredUnknownFieldParameters() throws IOException {
        return getTestFiles("unknown-field-entities.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testNonIgnoredUnknownFieldParameters")
    void testNonIgnoredUnknownField(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);

        try (HttpRosetteAPI tmpApi = new HttpRosetteAPI.Builder()
                .key("my-key-123")
                .url(String.format("http://localhost:%d/rest/v1", mockServer.getPort()))
                .onlyAcceptKnownFields(true)
                .build()) {
            tmpApi.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request, EntitiesResponse.class);
        } catch (RosetteRuntimeException e) {
            if (e.getCause() instanceof UnrecognizedPropertyException) {
                return;
            }
        }
        fail("Unknown field is ignored when it shouldn't be ");
    }

    private void verifyEntity(EntitiesResponse response, String responseStr) throws IOException {
        EntitiesResponse goldResponse = mapper.readValue(responseStr, EntitiesResponse.class);
        assertEquals(goldResponse.getEntities(), response.getEntities());
    }

    private static Stream<Arguments> testGetEntityURLParameters() throws IOException {
        return getTestFiles("-url-entities.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetEntityURLParameters")
    void testGetEntityURL(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            EntitiesResponse response = api.perform(AbstractRosetteAPI.ENTITIES_SERVICE_PATH, request,
                    EntitiesResponse.class);
            verifyEntity(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetCategoriesDocParameters() throws IOException {
        return getTestFiles("-doc-categories.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetCategoriesDocParameters")
    void testGetCategoriesDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            CategoriesResponse response = api.perform(AbstractRosetteAPI.CATEGORIES_SERVICE_PATH, request,
                    CategoriesResponse.class);
            verifyCategory(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyCategory(CategoriesResponse response, String responseStr) throws IOException {
        CategoriesResponse goldResponse = mapper.readValue(responseStr, CategoriesResponse.class);
        assertEquals(response.getCategories().size(), goldResponse.getCategories().size());
    }

    private static Stream<Arguments> testGetCategoriesURLParameters() throws IOException {
        return getTestFiles("-url-categories.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetCategoriesURLParameters")
    void testGetCategoriesURL(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            CategoriesResponse response = api.perform(AbstractRosetteAPI.CATEGORIES_SERVICE_PATH, request,
                    CategoriesResponse.class);
            verifyCategory(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testGetSyntaxDependenciesDocParameters() throws IOException {
        return getTestFiles("-doc-syntax_dependencies.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetSyntaxDependenciesDocParameters")
    void testGetSyntaxDependenciesDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            SyntaxDependenciesResponse response = api.perform(AbstractRosetteAPI.SYNTAX_DEPENDENCIES_SERVICE_PATH,
                    request, SyntaxDependenciesResponse.class);
            verifySyntaxDependency(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifySyntaxDependency(SyntaxDependenciesResponse response, String responseStr) throws IOException {
        SyntaxDependenciesResponse goldResponse = mapper.readValue(responseStr, SyntaxDependenciesResponse.class);
        assertEquals(response.getSentences().size(), goldResponse.getSentences().size());
    }

    private static Stream<Arguments> testGetSimilarTermsParameters() throws IOException {
        return getTestFiles("-similar_terms.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetSimilarTermsParameters")
    void testGetSimilarTerms(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            SimilarTermsResponse response = api.perform(AbstractRosetteAPI.SIMILAR_TERMS_SERVICE_PATH, request,
                    SimilarTermsResponse.class);
            verifySimilarTerms(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifySimilarTerms(SimilarTermsResponse response, String responseStr) throws IOException {
        SimilarTermsResponse goldResponse = mapper.readValue(responseStr, SimilarTermsResponse.class);
        assertEquals(response.getSimilarTerms().size(), goldResponse.getSimilarTerms().size());
    }

    private static Stream<Arguments> testGetRelationshipsDocParameters() throws IOException {
        return getTestFiles("-doc-relationships.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetRelationshipsDocParameters")
    void testGetRelationshipsDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            RelationshipsResponse response = api.perform(AbstractRosetteAPI.RELATIONSHIPS_SERVICE_PATH, request,
                    RelationshipsResponse.class);
            verifyRelationships(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyRelationships(RelationshipsResponse response, String responseStr) throws IOException {
        RelationshipsResponse goldResponse = mapper.readValue(responseStr, RelationshipsResponse.class);
        assertEquals(response.getRelationships().size(), goldResponse.getRelationships().size());
    }

    private static Stream<Arguments> testGetSentimentDocParameters() throws IOException {
        return getTestFiles("-doc-sentiment.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetSentimentDocParameters")
    void testGetSentimentDoc(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            SentimentResponse response = api.perform(AbstractRosetteAPI.SENTENCES_SERVICE_PATH, request,
                    SentimentResponse.class);
            verifySentiment(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifySentiment(SentimentResponse response, String responseStr) throws IOException {
        SentimentResponse goldResponse = mapper.readValue(responseStr, SentimentResponse.class);
        // this is minimal.
        assertNotNull(response.getEntities());
        assertEquals(response.getEntities().size(), goldResponse.getEntities().size());
    }

    private static Stream<Arguments> testGetSentimentURLParameters() throws IOException {
        return getTestFiles("-url-sentiment.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testGetSentimentURLParameters")
    void testGetSentimentURL(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        DocumentRequest<?> request = readValue(DocumentRequest.class, testFilename);
        try {
            SentimentResponse response = api.perform(AbstractRosetteAPI.SENTENCES_SERVICE_PATH, request,
                    SentimentResponse.class);
            verifySentiment(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private static Stream<Arguments> testNameDeduplicationParameters() throws IOException {
        return getTestFiles("-name-deduplication.json");
    }

    @ParameterizedTest(name = "testFilename: {0}; statusCode: {2}")
    @MethodSource("testNameDeduplicationParameters")
    void testNameDeduplication(String testFilename, String responseStr, int statusCode) throws IOException {
        setStatusCodeResponse(responseStr, statusCode);
        NameDeduplicationRequest request = readValueNameDeduplication(testFilename);
        try {
            NameDeduplicationResponse response = api.perform(AbstractRosetteAPI.NAME_DEDUPLICATION_SERVICE_PATH,
                    request, NameDeduplicationResponse.class);
            verifyNameDeduplication(response, responseStr);
        } catch (HttpRosetteAPIException e) {
            verifyException(e, responseStr);
        }
    }

    private void verifyNameDeduplication(NameDeduplicationResponse response, String responseStr) throws IOException {
        NameDeduplicationResponse goldResponse = mapper.readValue(responseStr, NameDeduplicationResponse.class);
        assertEquals(goldResponse.getResults(), response.getResults());
    }

    private NameDeduplicationRequest readValueNameDeduplication(String testFilename) throws IOException {
        File input = new File("src/test/mock-data/request", testFilename);
        return mapper.readValue(input, NameDeduplicationRequest.class);
    }
}
