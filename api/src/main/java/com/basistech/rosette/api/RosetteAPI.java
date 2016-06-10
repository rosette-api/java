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

import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.Options;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.RelationshipsOptions;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SentencesResponse;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.apimodel.jackson.DocumentRequestMixin;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.io.ByteStreams;
//import org.apache.commons.codec.language.bm.Lang;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * You can use the RosetteAPI to access Rosette API endpoints.
 * RosetteAPI is thread-safe and immutable.
 */
@ThreadSafe
@Immutable
public class RosetteAPI implements Closeable {

    public static final String DEFAULT_URL_BASE = "https://api.rosette.com/rest/v1";
    public static final String SERVICE_NAME = "RosetteAPI";
    public static final String BINDING_VERSION = getVersion();
    public static final String USER_AGENT_STR = SERVICE_NAME + "-Java/" + BINDING_VERSION;

    public static final String LANGUAGE_SERVICE_PATH = "/language";
    public static final String MORPHOLOGY_SERVICE_PATH = "/morphology";
    public static final String ENTITIES_SERVICE_PATH = "/entities";
    /**
     * @deprecated merged into the {@link #ENTITIES_SERVICE_PATH}.
     */
    @Deprecated
    public static final String ENTITIES_LINKED_SERVICE_PATH = "/entities/linked";
    public static final String CATEGORIES_SERVICE_PATH = "/categories";
    public static final String RELATIONSHIPS_SERVICE_PATH = "/relationships";
    public static final String SENTIMENT_SERVICE_PATH = "/sentiment";
    public static final String NAME_TRANSLATION_SERVICE_PATH = "/name-translation";
    public static final String NAME_SIMILARITY_SERVICE_PATH = "/name-similarity";
    public static final String TOKENS_SERVICE_PATH = "/tokens";
    public static final String SENTENCES_SERVICE_PATH = "/sentences";
    public static final String INFO_SERVICE_PATH = "/info";
    public static final String VERSION_CHECK_PATH = "/info?clientVersion=" + BINDING_VERSION;
    public static final String PING_SERVICE_PATH = "/ping";

    private static final Logger LOG = LoggerFactory.getLogger(RosetteAPI.class);

    private String key;
    private String urlBase = DEFAULT_URL_BASE;
    private int failureRetries;
    private LanguageCode language;
    private Options options;
    private String genre;
    private DocumentRequest.Builder apiBuilder;
    private ObjectMapper mapper;
    private CloseableHttpClient httpClient;
    private List<Header> customHeaders;

    /**
     * Constructs a Rosette API instance using an API key.
     *
     * @param key Rosette API key
     * @throws RosetteAPIException If the service is not compatible with the version of the binding.
     * @throws IOException         General IO exception
     */
    public RosetteAPI(String key) throws IOException, RosetteAPIException {
        this(key, DEFAULT_URL_BASE);
    }

    /**
     * Constructs a Rosette API instance using an API key and accepts an
     * alternate URL for testing purposes.
     *
     * @param key          Rosette API key
     * @param alternateUrl Alternate Rosette API URL
     * @throws RosetteAPIException If the service is not compatible with the version of the binding.
     * @throws IOException         General IO exception
     */
    public RosetteAPI(String key, String alternateUrl) throws IOException, RosetteAPIException {
        Objects.requireNonNull(alternateUrl, "alternateUrl cannot be null");
        this.key = key;
        urlBase = alternateUrl;
        if (urlBase.endsWith("/")) {
            urlBase = urlBase.substring(0, urlBase.length() - 1);
        }
        this.failureRetries = 1;
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
        customHeaders = new ArrayList<>();
        initHttpClient();
        apiBuilder = new DocumentRequest.Builder();
        checkVersionCompatibility();
    }

    /**
     * Returns the version of the binding.
     *
     * @return version of the binding
     */
    private static String getVersion() {
        Properties properties = new Properties();
        try (InputStream ins = RosetteAPI.class.getClassLoader().getResourceAsStream("version.properties")) {
            properties.load(ins);
        } catch (IOException e) {
            // should not happen
        }
        return properties.getProperty("version", "undefined");
    }

    /**
     * Sets the number of retries in case of failure (default is one).
     *
     * @param failureRetries number of retries
     */
    public void setFailureRetries(int failureRetries) {
        this.failureRetries = failureRetries >= 0 ? failureRetries : 1;
    }

    /**
     * Sets the Rosette API key.
     *
     * @param key Rosette API key
     */
    public void setAPIKey(String key) {
        this.key = key;
    }

    /**
     * Sets the language code.
     *
     * @param language LanguageCode passed by user
     */
    public void setLanguage(LanguageCode language) {
        this.language = language;
        apiBuilder.language(language);
    }

    /**
     * Sets the language code.
     *
     * @param language String to be converted to LanguageCode
     */
    public void setLanguage(String language) {
        this.language =  LanguageCode.ISO639IsValid(language) ? LanguageCode.lookupByISO639(language)
                : LanguageCode.UNKNOWN;
        apiBuilder.language(this.language);
    }

    /**
     * Return language code.
     *
     * @return source language
     */
    public LanguageCode getLanguageCode() {
        return language;
    }

    /**
     * Sets the genre.
     *
     * @param genre genre (i.e. social-media)
     */
    public void setGenre(String genre) {
        this.genre = genre;
        apiBuilder.genre(genre);
    }

    /**
     * Returns the genre.
     *
     * @return genre if being used by API
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the options for the specific search.
     *
     * @param o object of Options subclass for the particular search
     */
    public void setOptions(Options o) {
        this.options = o;
        apiBuilder.options(o);
    }

    /**
     * Returns options used by API.
     *
     * @return options
     */
    public Options getOptions() {
        return options;
    }

    /**
     *
     */
    private void initHttpClient() {
        List<Header> defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader(HttpHeaders.USER_AGENT, USER_AGENT_STR));
        defaultHeaders.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip"));
        if (key != null) {
            defaultHeaders.add(new BasicHeader("X-RosetteAPI-Key", key));
        }
        if (customHeaders.size() > 0) {
            defaultHeaders.addAll(customHeaders);
        }
        httpClient = HttpClients.custom().setDefaultHeaders(defaultHeaders).build();
    }

    /**
     * Add a custom HTTP header to pass to Rosette API.
     *
     * @param name header name
     * @param value header value
     */
    public void addCustomHeader(String name, String value) {
        addCustomHeader(new BasicHeader(name, value));
    }

    /**
     * Add a custom HTTP header to pass to Rosette API.
     *
     * @param header header
     */
    public void addCustomHeader(Header header) {
        customHeaders.add(header);
        initHttpClient();
    }

    /**
     * Gets information about the Rosette API, returns name, version, build number and build time.
     *
     * @return InfoResponse
     * @throws RosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public InfoResponse info() throws IOException, RosetteAPIException {
        return sendGetRequest(urlBase + INFO_SERVICE_PATH, InfoResponse.class);
    }

    /**
     * Checks binding version compatibility against the Rosette API server
     *
     * @return boolean true if compatible
     * @throws IOException
     * @throws RosetteAPIException
     */
    private boolean checkVersionCompatibility() throws IOException, RosetteAPIException {
        InfoResponse response = sendPostRequest("{ body: 'version check' }", urlBase + VERSION_CHECK_PATH, InfoResponse.class);
        if (!response.isVersionChecked()) {
            ErrorResponse errResponse = new ErrorResponse("incompatibleVersion",
                    "The server version is not compatible with binding version " + BINDING_VERSION);
            throw new RosetteAPIException(200, errResponse);
        }
        return true;
    }

    /**
     * Pings the Rosette API for a response indicting that the service is available.
     *
     * @return PingResponse
     * @throws RosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public PingResponse ping() throws IOException, RosetteAPIException {
        return sendGetRequest(urlBase + PING_SERVICE_PATH, PingResponse.class);
    }

    /**
     * Matches 2 names and returns a score in NameMatchingResponse.
     *
     * @param request request object with the names.
     * @return response
     * @throws RosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public NameSimilarityResponse getNameSimilarity(NameSimilarityRequest request) throws RosetteAPIException, IOException {
        return sendPostRequest(request, urlBase + NAME_SIMILARITY_SERVICE_PATH, NameSimilarityResponse.class);
    }

    /**
     * Translates a name into the target language specified in NameTranslationRequest.
     *
     * @param request NameTranslationRequest contains the name to be translated and the target language.
     * @return the response.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getNameTranslation(String)
     */
    @Deprecated
    public NameTranslationResponse getNameTranslation(NameTranslationRequest request)
            throws RosetteAPIException, IOException {
        return sendPostRequest(request, urlBase + NAME_TRANSLATION_SERVICE_PATH, NameTranslationResponse.class);
    }

    /**
     * Translates a name into the target language specified by the API object.
     *
     * @param content Name to be translated.
     *
     * @return translated name
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public NameTranslationResponse getNameTranslation(String content) throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + NAME_TRANSLATION_SERVICE_PATH, NameTranslationResponse.class);
    }

    /**
     * Performs language identification on data read from an InputStream. Return a list of languages.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type (e.g. text/html)
     * @param options     Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated Replaced by getLanguage(InputStream, String)
     */
    @Deprecated
    public LanguageResponse getLanguage(InputStream inputStream, String contentType, LanguageOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder().contentBytes(bytes, contentType)
                .options(options).build();
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data from an InputSteam. Builds request object from API, not parameters.
     * Returns a list of languages.
     *
     * @param inputStream Input stream of file
     * @param contentType the content type (e.g. text/html)
     *
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - There's a problem with the Rosette API request
     * @throws IOException         - There's a problem with communication or JSON serialization/deserialization
     */
    public LanguageResponse getLanguage(InputStream inputStream, String contentType) throws RosetteAPIException, IOException {
        byte[]  bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from an URL. Return a list of languages.
     *
     * @param url     URL for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated Replaced by getLanguage(URL)
     */
    @Deprecated
    public LanguageResponse getLanguage(URL url, LanguageOptions options) throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from an URL. Builds request object from API, not parameters.
     * Returns a list of languages.
     *
     * @param url URL for language detection
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(URL url) throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from a string. Return a list of languages.
     *
     * @param content String content for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated Replaced by getLanguage(String)
     */
    @Deprecated
    public LanguageResponse getLanguage(String content, LanguageOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder().content(content)
                .options(options).build();
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from a string. Builds request object from API not parameters.
     * Returns a list of languages.
     *
     * @param content String content for language detection.
     *
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     * descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content) throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Returns morphological analysis of the input file.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     *
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param inputStream          Input stream of file.
     * @param contentType          the content type (e.g. text/html)
     * @param language             Language of input if known (see {@link LanguageCode}), or null
     * @param options              Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getMorphology(inputStream, contentType, MorphologicalFeature)
     */
    @Deprecated
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature,
                                            InputStream inputStream,
                                            String contentType,
                                            LanguageCode language, MorphologyOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the input file. The response may include lemmas, part of speech tags,
     * compound word components, and Han readings. Builds request object from API rather than from parameters.
     * Support for specific return types depends on language.
     *
     * @param inputStream Input stream of file
     * @param contentType The content type (e.g. text/html)
     * @return MorphologyResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature,
                                            InputStream inputStream,
                                            String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the URL content.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     *
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param url                  URL containing the data.
     * @param language             Language of input if known (see {@link LanguageCode}), or null
     * @param options              Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getMorphology(URL, MorphologicalFeature)
     */
    @Deprecated
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, URL url,
                                            LanguageCode language,
                                            MorphologyOptions options) throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the URL content. The response may include lemmas, part of speech tags, compound
     * word components, and Han readings. Builds request object from API, rather than from parameters. Support for
     * specific return types depends on language.
     *
     * @param url URL containing the data
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a problem with communication or JSON serialization/deserialization.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, URL url)
            throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     *
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content              String containing the data.
     * @param language             Language of input if known (see {@link LanguageCode}), or null
     * @param options              Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getMorphology(String, MorphologicalFeature)
     */
    @Deprecated
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content,
                                            LanguageCode language, MorphologyOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string. The response may include lemmas, part of speech tags, compound word
     * components, and Han reading. Constructs request object from API information rather than from parameters. Support
     * for specific return types depends on language.
     *
     * @param content String containing the data.
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + MORPHOLOGY_SERVICE_PATH + "/" + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }


    /**
     * Returns entities extracted from the input file.
     * <p>
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type of the data (e.g. text/html)
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @param options     EntityMention options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaces by getEntities(InputStream, String)
     */
    @Deprecated
    public EntitiesResponse getEntities(InputStream inputStream,
                                        String contentType,
                                        LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Returns entities extracted from the input file. Builds request object from API fields not from parameters.
     * <p>
     * The response is a list of extracted entities. Each entity includes chain ID (all instances of the same
     * entity share a chain id), mention (entity text in the input), normalized text (the most complete form
     * of this entity that appears in the input), count (how many times this entity appears in the input), and the
     * confidence associated with the extraction.
     *
     * @param inputStream Input stream of file.
     * @param contentType The content type of the data (e.g. text/html)
     * @return EntitiesResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public EntitiesResponse getEntities(InputStream inputStream, String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }


    /**
     * Returns entities extracted from the URL content.
     * <p>
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  EntityMention options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getEntities(URL)
     */
    @Deprecated
    public EntitiesResponse getEntities(URL url, LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     *Returns entities extracted from the URL content. Request object built through API rather than parameters.
     * <p>
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param url URL containing the data.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(URL url)
            throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Returns entities extracted from a string.
     * <p>
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  EntityMention options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getEntities(String)
     */
    @Deprecated
    public EntitiesResponse getEntities(String content, LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     *Returns entities extracted from a string. Request object built from API rather than from parameters.
     * <p>
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param content String containing the data.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Links entities in the input file to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type for the file (e.g. text/html).
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     * @deprecated Merged into {@link #getEntities(String, LanguageCode, EntitiesOptions)}.
     */
    @Deprecated
    public LinkedEntitiesResponse getLinkedEntities(InputStream inputStream,
                                                    String contentType,
                                                    LanguageCode language)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Links entities in the URL content to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     * @deprecated Merged into {@link #getEntities(InputStream, String, LanguageCode, EntitiesOptions)}
     */
    @Deprecated
    public LinkedEntitiesResponse getLinkedEntities(URL url, LanguageCode language)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    @SuppressWarnings("deprecation")
    public LinkedEntitiesResponse getLinkedEntities(String content, LanguageCode language)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .build();
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * <p>
     * The response is the contextual categories identified in the input.
     *
     * @param inputStream Input stream of file.
     * @param contentType the contentType of the file (e.g. text/html).
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @param options     CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getCategories(InputStream, String)
     */
    @Deprecated
    public CategoriesResponse getCategories(InputStream inputStream,
                                                 String contentType,
                                                 LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * The request object is built from the API object rather than from the parameters.
     * <p>
     * The response is the contextual categories identified in the input.
     * @param inputStream Input stream of file.
     * @param contentType The contentType of the file (e.g. text/html)
     * @return CategoriesResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public CategoriesResponse getCategories(InputStream inputStream,
                                            String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the URL content. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * <p>
     * The response is the contextual categories identified in the input.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated Replaced by getCategories(URL)
     */
    @Deprecated
    public CategoriesResponse getCategories(URL url, LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * The request object is built from the API object rather than from the parameters.
     * <p>
     * The response is the contextual categories identified in the input.
     *
     * @param url URL containing the data.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoriesResponse getCategories(URL url)
            throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * <p>
     * The response is the contextual categories identified in the input.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getCategories(String)
     */
    @Deprecated
    public CategoriesResponse getCategories(String content, LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     * The request object is built from the API object rather than from the parameters.
     * <p>
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a problem with communication or JSON serialization/deserialization.
     */
    public CategoriesResponse getCategories(String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns each relationship extracted from the input.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     *
     * @param content, String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  RelationshipOptions
     * @return RelationshipsResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getRelationships(String)
     */
    @Deprecated
    public RelationshipsResponse getRelationships(String content, LanguageCode language, RelationshipsOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     *
     * Returns each relationship extracted from the input. Request object uses API attributes rather than parameters.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     * @param content String containing the data.
     * @return RelationshipsResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public RelationshipsResponse getRelationships(String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     * Returns each relationship extracted from the input.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type of the file.
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @param options     RelationshipOptions
     * @return RelationshipsResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error
     *
     * @deprecated Replaced by getRelationships(InputStream, String)
     */
    @Deprecated
    public RelationshipsResponse getRelationships(InputStream inputStream,
                                                  String contentType,
                                                  LanguageCode language, RelationshipsOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     * Returns each relationship extracted from the input. Request object uses API attributes rather than parameters.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     * @param inputStream Input stream of file.
     * @param contentType The content type of the file.
     * @return RelationshipsResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public RelationshipsResponse getRelationships(InputStream inputStream,
                                                  String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     * Returns each relationship extracted from the input.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  RelationshipOptions
     * @return RelationshipsResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error
     *
     * @deprecated replaced getRelationships(URL)
     */
    @Deprecated
    public RelationshipsResponse getRelationships(URL url, LanguageCode language, RelationshipsOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     * Returns each relationship extracted from the input.
     * <p>
     * The response is a list of extracted relationships. A relationship contains
     * <p>
     * predicate - usually the main verb, property or action that is expressed by the text
     * arg1 - usually the subject, agent or main actor of the relationship
     * arg2 [optional] - complements the predicate and is usually the object, theme or patient of the relationship
     * arg3 [optional] - usually an additional object in ditransitive verbs
     * adjuncts [optional] - contain all optional parts of a relationship which are not temporal or locative expressions
     * locatives [optional] - usually express the locations the action expressed by the relationship took place
     * temporals [ optional] - usually express the time in which the action expressed by the relationship took place
     * confidence - a measure of quality of relationship extraction, between 0 - 1
     *
     * @param url URL containing the data.
     * @return RelationshipOptions
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a problem with communication or JSON serialization/deserialization.
     */
    public RelationshipsResponse getRelationships(URL url)
            throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + RELATIONSHIPS_SERVICE_PATH, RelationshipsResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type of the file (e.g. text/html)
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @param options     SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentiment(InputStream, String)
     */
    @Deprecated
    public SentimentResponse getSentiment(InputStream inputStream,
                                          String contentType,
                                          LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input. Request object built from API rather than
     * from individual request.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param inputStream Input stream of file.
     * @param contentType The content type of the file (e.g. text/html)
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(InputStream inputStream,
                                          String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentiment(URL)
     */
    @Deprecated
    public SentimentResponse getSentiment(URL url, LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input. Request object built from API rather than
     * from individual request.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param url URL containing the data.
     * @return SentimentOptions
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(URL url)
            throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @param options  SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentiment(String)
     */
    @Deprecated
    public SentimentResponse getSentiment(String content, LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .options(options)
                .build();
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input. Request object uses API object rather than
     * parameters.
     * <p>
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @return SentimentOptions
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type of the file (e.g. text/html)
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getTokens(InputStream, String)
     */
    @Deprecated
    public TokensResponse getTokens(InputStream inputStream, String contentType, LanguageCode language)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .build();
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens. Request object built through API calls rather than from parameters.
     *
     * @param inputStream Input stream of file.
     * @param contentType The content type of the file (e.g. text/html)
     *
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a problem with communication or JSON serialization/deserialization
     */
    public TokensResponse getTokens(InputStream inputStream, String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getTokens(URL)
     */
    @Deprecated
    public TokensResponse getTokens(URL url, LanguageCode language) throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .build();
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens. Request object uses API fields rather than call parameters.
     *
     * @param url URL containing the data.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public TokensResponse getTokens(URL url) throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getTokens(String)
     */
    @Deprecated
    public TokensResponse getTokens(String content, LanguageCode language) throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .build();
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens. Request object is built from the API object rather than from parameters.
     *
     * @param content String containing the data.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public TokensResponse getTokens(String content) throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param inputStream Input stream of file.
     * @param contentType the content type of the file (e.g. text/html).
     * @param language    Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentences(InputStream, String)
     */
    @Deprecated
    public SentencesResponse getSentences(InputStream inputStream,
                                          String contentType,
                                          LanguageCode language)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentBytes(bytes, contentType)
                .build();
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences. Request object is built from the API object rather than from parameters.
     *
     * @param inputStream Input stream of file.
     * @param contentType The content type of the file (e.g. text/html).
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a problem with communication or JSON serialization/deserialization.
     */
    public SentencesResponse getSentences(InputStream inputStream,
                                          String contentType)
            throws RosetteAPIException, IOException {
        byte[] bytes = getBytes(inputStream);
        apiBuilder.contentBytes(bytes, contentType);
        return sendPostRequest(apiBuilder.build(), urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param url      URL containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentences(URL)
     */
    @Deprecated
    public SentencesResponse getSentences(URL url, LanguageCode language) throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .contentUri(url.toString())
                .build();
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences. Request object is built from the API object rather than from parameters.
     *
     * @param url URL containing the data.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication of JSON serialization/deserialization error.
     */
    public SentencesResponse getSentences(URL url) throws RosetteAPIException, IOException {
        apiBuilder.contentUri(url.toString());
        return sendPostRequest(apiBuilder.build(), urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content  String containing the data.
     * @param language Language of input if known (see {@link LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     *
     * @deprecated replaced by getSentences(String)
     */
    @Deprecated
    public SentencesResponse getSentences(String content, LanguageCode language)
            throws RosetteAPIException, IOException {
        Request request = new DocumentRequest.Builder()
                .language(language)
                .content(content)
                .build();
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences. Request object is built from the API object rather than from parameters.
     *
     * @param content String containing the data.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException         - If there is a communication or JSON serialization/deserialization error.
     */
    public SentencesResponse getSentences(String content)
            throws RosetteAPIException, IOException {
        apiBuilder.content(content);
        return sendPostRequest(apiBuilder.build(), urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Provides information on Rosette API
     *
     * @return {@link com.basistech.rosette.apimodel.InfoResponse InfoResponse}
     * @throws RosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public InfoResponse getInfo() throws RosetteAPIException, IOException {
        return sendGetRequest(urlBase + INFO_SERVICE_PATH, InfoResponse.class);
    }

    /**
     * Send a request to the API, return a response (or throw an exception).
     * Use this method for request parameter combinations that are not covered
     * by the specific endpoint methods.
     * @param endpoint The endpoint, for example, '/entities'.
     * @param request The request object.
     * @param responseClass The class of the response object corresponding to the request object.
     * @param <Req> the request type.
     * @param <Res> the response type.
     * @return the response.
     * @throws IOException for errors in communications.
     * @throws RosetteAPIException for errors returned by the API.
     */
    public <Req extends Request, Res extends Response> Res doRequest(String endpoint, Req request, Class<Res> responseClass)
            throws IOException, RosetteAPIException {
        return sendPostRequest(request, urlBase + endpoint, responseClass);
    }

    /**
     * Sends a GET request to Rosette API.
     * <p>
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz  Response class
     * @return Response
     * @throws IOException
     * @throws RosetteAPIException
     */
    private <T extends Response> T sendGetRequest(String urlStr, Class<T> clazz) throws IOException, RosetteAPIException {
        HttpGet get = new HttpGet(urlStr);
        HttpResponse httpResponse = httpClient.execute(get);
        T resp = getResponse(httpResponse, clazz);
        responseHeadersToExtendedInformation(resp, httpResponse);
        return resp;
    }

    /**
     * Sends a POST request to Rosette API.
     * <p>
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz  Response class
     * @return Response
     * @throws RosetteAPIException
     * @throws IOException
     */
    private <T extends Response> T sendPostRequest(Object request, String urlStr, Class<T> clazz)
            throws RosetteAPIException, IOException {
        ObjectWriter writer = mapper.writer().without(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        boolean notPlainText = false;
        if (request instanceof DocumentRequest) {
            Object rawContent = ((DocumentRequest) request).getRawContent();
            if (rawContent instanceof String) {
                writer = writer.withView(DocumentRequestMixin.Views.Content.class);
            } else if (rawContent != null) {
                notPlainText = true;
            }
        }
        final ObjectWriter finalWriter = writer;

        HttpPost post = new HttpPost(urlStr);
        //TODO: add compression!
        if (notPlainText) {
            setupMultipartRequest((DocumentRequest) request, finalWriter, post);
        } else {
            setupPlainRequest(request, finalWriter, post);
        }

        RosetteAPIException lastException = null;
        int numRetries = this.failureRetries;
        while (numRetries-- > 0) {
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(post);

                T resp = getResponse(response, clazz);
                Header ridHeader = response.getFirstHeader("X-RosetteAPI-DocumentRequest-Id");
                if (ridHeader != null && ridHeader.getValue() != null) {
                    LOG.debug("DocumentRequest ID " + ridHeader.getValue());
                }
                responseHeadersToExtendedInformation(resp, response);
                return resp;
            } catch (RosetteAPIException e) {
                // only 5xx errors are worthy retrying, others throw right away
                if (e.getHttpStatusCode() < 500) {
                    throw e;
                } else {
                    lastException = e;
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        throw lastException;
    }

    @SuppressWarnings("unchecked")
    private <T extends Response> void responseHeadersToExtendedInformation(T resp, HttpResponse response) {
        for (Header header : response.getAllHeaders()) {
            if (resp.getExtendedInformation() != null
                    && resp.getExtendedInformation().containsKey(header.getName())) {
                Set<Object> currentSetValue;
                if (resp.getExtendedInformation().get(header.getName()) instanceof Set) {
                    currentSetValue = (Set<Object>) resp.getExtendedInformation().get(header.getName());
                } else {
                    currentSetValue = new HashSet<>(Collections.singletonList(resp.getExtendedInformation().get(header.getName())));
                }
                currentSetValue.add(header.getValue());
                resp.setExtendedInformation(header.getName(), currentSetValue);
            } else {
                resp.setExtendedInformation(header.getName(), header.getValue());
            }
        }
    }

    private void setupPlainRequest(final Object request, final ObjectWriter finalWriter, HttpPost post) {
        // just posting json.
        post.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        post.setEntity(new AbstractHttpEntity() {
            @Override
            public boolean isRepeatable() {
                return false;
            }

            @Override
            public long getContentLength() {
                return -1;
            }

            @Override
            public InputStream getContent() throws IOException, UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeTo(OutputStream outstream) throws IOException {
                finalWriter.writeValue(outstream, request);
            }

            @Override
            public boolean isStreaming() {
                return false;
            }
        });
    }

    private void setupMultipartRequest(final DocumentRequest request, final ObjectWriter finalWriter, HttpPost post) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMimeSubtype("mixed");
        builder.setMode(HttpMultipartMode.STRICT);

        FormBodyPartBuilder partBuilder = FormBodyPartBuilder.create("request",
                // Make sure we're not mislead by someone who puts a charset into the mime type.
                new AbstractContentBody(ContentType.parse(ContentType.APPLICATION_JSON.getMimeType())) {
                    @Override
                    public String getFilename() {
                        return null;
                    }

                    @Override
                    public void writeTo(OutputStream out) throws IOException {
                        finalWriter.writeValue(out, request);
                    }

                    @Override
                    public String getTransferEncoding() {
                        return MIME.ENC_BINARY;
                    }

                    @Override
                    public long getContentLength() {
                        return -1;
                    }
                });

        // Either one of 'name=' or 'Content-ID' would be enough.
        partBuilder.setField(MIME.CONTENT_DISPOSITION, "inline;name=\"request\"");
        partBuilder.setField("Content-ID", "request");

        builder.addPart(partBuilder.build());

        partBuilder = FormBodyPartBuilder.create("content", new InputStreamBody(request.getContentBytes(), ContentType.parse(request.getContentType())));
        partBuilder.setField(MIME.CONTENT_DISPOSITION, "inline;name=\"content\"");
        partBuilder.setField("Content-ID", "content");
        builder.addPart(partBuilder.build());
        builder.setCharset(StandardCharsets.UTF_8);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
    }

    private String headerValueOrNull(Header header) {
        if (header == null) {
            return null;
        } else {
            return header.getValue();
        }
    }

    /**
     * Gets response from HTTP connection, according to the specified response class;
     * throws for an error response.
     *
     * @param httpResponse the response object
     * @param clazz  Response class
     * @return Response
     * @throws IOException
     * @throws RosetteAPIException if the status is not 200.
     */
    private <T extends Response> T getResponse(HttpResponse httpResponse, Class<T> clazz)
            throws IOException, RosetteAPIException {
        int status = httpResponse.getStatusLine().getStatusCode();
        String encoding = headerValueOrNull(httpResponse.getFirstHeader(HttpHeaders.CONTENT_ENCODING));
        try (
                InputStream stream = httpResponse.getEntity().getContent();
                InputStream inputStream = "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(stream) : stream) {
            String ridHeader = headerValueOrNull(httpResponse.getFirstHeader("X-RosetteAPI-DocumentRequest-Id"));
            if (HTTP_OK != status) {
                String ecHeader = headerValueOrNull(httpResponse.getFirstHeader("X-RosetteAPI-Status-Code"));
                String emHeader = headerValueOrNull(httpResponse.getFirstHeader("X-RosetteAPI-Status-Message"));
                String responseContentType = headerValueOrNull(httpResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE));
                if ("application/json".equals(responseContentType)) {
                    ErrorResponse errorResponse = mapper.readValue(inputStream, ErrorResponse.class);
                    if (ridHeader != null) {
                        LOG.debug("DocumentRequest ID " + ridHeader);
                    }
                    if (ecHeader != null) {
                        errorResponse.setCode(ecHeader);
                    }
                    if (emHeader != null) {
                        errorResponse.setMessage(emHeader);
                    }
                    throw new RosetteAPIException(status, errorResponse);
                } else {
                    String errorContent;
                    if (inputStream != null) {
                        byte[] content = getBytes(inputStream);
                        errorContent = new String(content, "utf-8");
                    } else {
                        errorContent = "(no body)";
                    }
                    // something not from us at al
                    throw new RosetteAPIException(status, new ErrorResponse("invalidErrorResponse", errorContent));
                }
            } else {
                return mapper.readValue(inputStream, clazz);
            }
        }
    }

    /**
     * Returns a byte array from InputStream.
     *
     * @param is InputStream
     * @return byte array
     * @throws IOException
     */
    private static byte[] getBytes(InputStream is) throws IOException {
        return ByteStreams.toByteArray(is);
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}
