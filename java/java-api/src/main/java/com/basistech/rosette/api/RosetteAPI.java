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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.xml.bind.DatatypeConverter;

import com.basistech.rosette.apimodel.CategoryOptions;
import com.basistech.rosette.apimodel.CategoryRequest;
import com.basistech.rosette.apimodel.CategoryResponse;
import com.basistech.rosette.apimodel.ConstantsResponse;
import com.basistech.rosette.apimodel.EntityOptions;
import com.basistech.rosette.apimodel.EntityRequest;
import com.basistech.rosette.apimodel.EntityResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.LanguageInfoResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LinguisticsOptions;
import com.basistech.rosette.apimodel.LinguisticsRequest;
import com.basistech.rosette.apimodel.LinkedEntityRequest;
import com.basistech.rosette.apimodel.LinkedEntityResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameMatcherRequest;
import com.basistech.rosette.apimodel.NameMatcherResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SentenceResponse;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokenResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * You can use the RosetteAPI to access Rosette API endpoints.
 * RosetteAPI is thread-safe and immutable.
 */
public class RosetteAPI {
    public static final String DEFAULT_URL_BASE = "https://api.rosette.com/rest/v1";

    private static final String LANGUAGE_SERVICE_PATH = "/language";
    private static final String MORPHOLOGY_SERVICE_PATH = "/morphology/";
    private static final String ENTITIES_SERVICE_PATH = "/entities";
    private static final String ENTITIES_LINKED_SERVICE_PATH = "/entities/linked";
    private static final String CATEGORIES_SERVICE_PATH = "/categories";
    private static final String SENTIMENT_SERVICE_PATH = "/sentiment";
    private static final String TRANSLATED_NAME_SERVICE_PATH = "/translated-name";
    private static final String MATCHED_NAME_SERVICE_PATH = "/matched-name";
    private static final String TOKENS_SERVICE_PATH = "/tokens";
    private static final String SENTENCES_SERVICE_PATH = "/sentences";
    private static final String INFO_SERVICE_PATH = "/info";
    private static final String PING_SERVICE_PATH = "/ping";
    private static final String DEBUG_PARAM_ON = "?debug=true";
    private static final String DEBUG_PARAM_OFF = "";

    private String key;
    private String urlBase = DEFAULT_URL_BASE;
    private String debugOutput = "";
    private ObjectMapper mapper;

    /**
     * Specify which feature you want Rosette API Morphology endpoint to return. Specify COMPLETE for every feature.
     */
    public enum MorphologicalFeature {
        COMPLETE("complete"),
        LEMMAS("lemmas"),
        PARTS_OF_SPEECH("parts-of-speech"),
        COMPOUND_COMPONENTS("compound-components"),
        HAN_READINGS("han-readings");

        private String pathLabel;

        MorphologicalFeature(String pathLabel) {
            this.pathLabel = pathLabel;
        }

        @Override
        public String toString() {
            return pathLabel;
        }
    }

    public enum EndpointInfo {
        ENTITIES("Entities", ENTITIES_SERVICE_PATH),
        LINKED_ENTITIES("Linked Entities", ENTITIES_LINKED_SERVICE_PATH),
        CATEGORIES("Categories", CATEGORIES_SERVICE_PATH),
        SENTIMENT("Sentiment", SENTIMENT_SERVICE_PATH),
        TRANSLATED_NAME("Translated Name", TRANSLATED_NAME_SERVICE_PATH),
        MATCHED_NAME("Matched Name", MATCHED_NAME_SERVICE_PATH);

        private String description;
        private String servicePath;

        EndpointInfo(String description, String servicePath) {
            this.description = description;
            this.servicePath = servicePath;
        }

        public String getDescription() { return description; }
        private String getServicePath() { return servicePath; }
    }

    /**
     * Constructs a Rosette API instance using an API key.
     *
     * @param key Rosette API key
     */
    public RosetteAPI(String key) {
        this.key = key;
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    /**
     * Constructs a Rosette API instance without an API key.
     */
    public RosetteAPI() {
        this("");
    }

    /**
     * Sets the base URL of the Rosette service.
     * @param url The base URL
     */
    public void setUrlBase(String url) {
        urlBase = url;
        if (!urlBase.endsWith("/")) {
            urlBase += "/";
        }
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
     * Toggles Rosette API debug output.
     * @param flag true for debug output in RosetteAPIException message.
     */
    public void setDebugOutput(boolean flag) {
        this.debugOutput = flag ? DEBUG_PARAM_ON : DEBUG_PARAM_OFF;
    }

    /**
     * Gets information about the Rosette API, returns name, version, build number and build time.
     * @return InfoResponse
     * @throws IOException
     * @throws RosetteAPIException
     */
    public InfoResponse info() throws IOException, RosetteAPIException {
        return sendGetRequest(urlBase + INFO_SERVICE_PATH, InfoResponse.class);
    }

    /**
     * Pings the Rosette API for a response indicting that the service is available.
     * @return PingResponse
     * @throws IOException
     * @throws RosetteAPIException
     */
    public PingResponse ping() throws IOException, RosetteAPIException {
        return sendGetRequest(urlBase + PING_SERVICE_PATH, PingResponse.class);
    }

    /**
     * Matches 2 names and returns a score in NameMatcherResponse.
     * @param request NameMatcherRequest contains 2 names.
     * @return NameMatcherResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public NameMatcherResponse matchName(NameMatcherRequest request) throws RosetteAPIException, IOException {
        return sendRequest(request, urlBase + MATCHED_NAME_SERVICE_PATH, NameMatcherResponse.class);
    }

    /**
     * Translates a name into the target language specified in NameTranslationRequest.
     * @param request NameTranslationRequest contains the name to be translated and the target language.
     * @return NameTranslationResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public NameTranslationResponse translateName(NameTranslationRequest request) throws RosetteAPIException, IOException {
        return sendRequest(request, urlBase + TRANSLATED_NAME_SERVICE_PATH, NameTranslationResponse.class);
    }

    /**
     * Performs language identification on data read from an InputStream. Return a list of languages.
     * @param inputStream Input stream of file.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(InputStream inputStream, LanguageOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LanguageRequest request = new LanguageRequest(encodedStr, null, "text/html", null, options);
        return sendRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from an URL. Return a list of languages.
     * @param url URL for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(URL url, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(null, url.toString(), null, null, options);
        return sendRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from a string. Return a list of languages.
     * @param content String content for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, null, options);
        return sendRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Returns a list of candidate languages in order of descending confidence from a string.
     * @param content String content for language detection.
     * @param unit The unit of content. Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content, InputUnit unit, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, unit, options);
        return sendRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Returns morphological analysis of the input file.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, InputStream inputStream, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, options);
        return sendRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the URL content.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, URL url, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, options);
        return sendRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, options);
        return sendRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content, String language, InputUnit unit, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, options);
        return sendRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns each entity extracted from the input file.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in the input),
     * count (how many times this entity appears in the input), and the confidence associated with the extraction.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(InputStream inputStream, String language, EntityOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        EntityRequest request = new EntityRequest(language, encodedStr, null, "text/html", null, options);
        return sendRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntityResponse.class);
    }

    /**
     * Returns each entity extracted from the URL content.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in the input),
     * count (how many times this entity appears in the input), and the confidence associated with the extraction.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(URL url, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, null, url.toString(), null, null, options);
        return sendRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntityResponse.class);
    }

    /**
     * Returns each entity extracted from a string.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in the input),
     * count (how many times this entity appears in the input), and the confidence associated with the extraction.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(String content, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, null, options);
        return sendRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntityResponse.class);
    }

    /**
     * Returns each entity extracted from a string.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in the input),
     * count (how many times this entity appears in the input), and the confidence associated with the extraction.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(String content, String language, InputUnit unit, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, unit, options);
        return sendRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntityResponse.class);
    }

    /**
     * Links entities in the input file to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(InputStream inputStream, String language) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinkedEntityRequest request = new LinkedEntityRequest(language, encodedStr, null, "text/html", null);
        return sendRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntityResponse.class);
    }

    /**
     * Links entities in the URL content to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(URL url, String language) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, null, url.toString(), null, null);
        return sendRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntityResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(String content, String language) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, null);
        return sendRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntityResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(String content, String language, InputUnit unit) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, unit);
        return sendRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntityResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(InputStream inputStream, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        CategoryRequest request = new CategoryRequest(language, encodedStr, null, "text/html", null, options);
        return sendRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the URL content. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(URL url, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, null, url.toString(), null, null, options);
        return sendRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(String content, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, null, options);
        return sendRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(String content, String language, InputUnit unit, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, unit, options);
        return sendRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoryResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(InputStream inputStream, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        SentimentRequest request = new SentimentRequest(language, encodedStr, null, "text/html", null, options);
        return sendRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(URL url, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, null, url.toString(), null, null, options);
        return sendRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, null, options);
        return sendRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content, String language, InputUnit unit, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, unit, options);
        return sendRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(InputStream inputStream, String language) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, null);
        return sendRequest(request, urlBase + TOKENS_SERVICE_PATH, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(URL url, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, null);
        return sendRequest(request, urlBase + TOKENS_SERVICE_PATH, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(String content, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, null);
        return sendRequest(request, urlBase + TOKENS_SERVICE_PATH, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(String content, String language, InputUnit unit) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, null);
        return sendRequest(request, urlBase + TOKENS_SERVICE_PATH, TokenResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(InputStream inputStream, String language) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, null);
        return sendRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(URL url, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, null);
        return sendRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(String content, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, null);
        return sendRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(String content, String language, InputUnit unit) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, null);
        return sendRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentenceResponse.class);
    }

    public LanguageInfoResponse getLanguageInfo() throws RosetteAPIException, IOException {
        return sendGetRequest(urlBase + LANGUAGE_SERVICE_PATH + INFO_SERVICE_PATH, LanguageInfoResponse.class);
    }

    public ConstantsResponse getInfo(EndpointInfo endpointInfo) throws RosetteAPIException, IOException {
        return sendGetRequest(urlBase + endpointInfo.getServicePath() + INFO_SERVICE_PATH, ConstantsResponse.class);
    }

    /**
     * Sends a GET request to Rosette API.
     *
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz Response class
     * @return Response
     * @throws IOException
     * @throws RosetteAPIException
     */
    private <T extends Response> T  sendGetRequest(String urlStr, Class<T> clazz) throws IOException, RosetteAPIException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("GET");
        return clazz.cast(getResponse(httpUrlConnection, clazz));
    }

    /**
     * Sends a POST request to Rosette API.
     *
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz Response class
     * @return Response
     * @throws RosetteAPIException
     * @throws IOException
     */
    private <T extends Response> T sendRequest(Object request, String urlStr, Class<T> clazz) throws RosetteAPIException, IOException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("POST");
        try (OutputStream os = httpUrlConnection.getOutputStream()) {
            mapper.writeValue(os, request);
        }
        return clazz.cast(getResponse(httpUrlConnection, clazz));
    }

    /**
     * Opens HTTP connection.
     * Returns HTTP connection.
     * @param urlStr Rosette API end point.
     * @return HttpURLConnection
     * @throws IOException
     */
    private HttpURLConnection openHttpURLConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr + debugOutput);
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoOutput(true);
        if (key == null) {
            key = "";
        }
        httpUrlConnection.setRequestProperty("user_key", key);
        httpUrlConnection.setRequestProperty("Content-Type", "application/json");
        httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip");
        return httpUrlConnection;
    }

    /**
     * Gets response from HTTP connection, according to the specified response class.
     *
     * @param httpUrlConnection
     * @param clazz Response class
     * @return Response
     * @throws IOException
     * @throws RosetteAPIException
     */
    private Response getResponse(HttpURLConnection httpUrlConnection, Class<? extends Response> clazz) throws IOException, RosetteAPIException {
        int status = httpUrlConnection.getResponseCode();
        String encoding = httpUrlConnection.getContentEncoding();
        try (
            InputStream stream = status != HTTP_OK ? httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
            InputStream inputStream =
                "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(stream) : stream
        ) {
            if (HTTP_OK != status) {
                ErrorResponse errorResponse = mapper.readValue(inputStream, ErrorResponse.class);
                throw new RosetteAPIException(status, errorResponse);
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
        int len;
        int size = 1024;
        byte[] buf;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();
        return buf;
    }
}
