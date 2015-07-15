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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basistech.rosette.apimodel.CategoriesRequest;
import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.LanguageCode;
import com.basistech.rosette.apimodel.LanguageInfoResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LinkedEntitiesRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.NameMatchingRequest;
import com.basistech.rosette.apimodel.NameMatchingResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SentencesResponse;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * You can use the RosetteAPI to access Rosette API endpoints.
 * RosetteAPI is thread-safe and immutable.
 */
public final class RosetteAPI {
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
    private int failureRetries;
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

    /**
     * Constructs a Rosette API instance using an API key.
     *
     * @param key Rosette API key
     */
    public RosetteAPI(String key) {
        this.key = key;
        this.failureRetries = 1;
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
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
     * Sets the number of retries in case of failure (default is one).
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
     * Matches 2 names and returns a score in NameMatchingResponse.
     * @param request NameMatchingRequest contains 2 names.
     * @return NameMatchingResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public NameMatchingResponse matchName(NameMatchingRequest request) throws RosetteAPIException, IOException {
        return sendPostRequest(request, urlBase + MATCHED_NAME_SERVICE_PATH, NameMatchingResponse.class);
    }

    /**
     * Translates a name into the target language specified in NameTranslationRequest.
     * @param request NameTranslationRequest contains the name to be translated and the target language.
     * @return NameTranslationResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public NameTranslationResponse translateName(NameTranslationRequest request)
            throws RosetteAPIException, IOException {
        return sendPostRequest(request, urlBase + TRANSLATED_NAME_SERVICE_PATH, NameTranslationResponse.class);
    }

    /**
     * Performs language identification on data read from an InputStream. Return a list of languages.
     * @param inputStream Input stream of file.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     *         descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(InputStream inputStream, LanguageOptions options)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LanguageRequest request = new LanguageRequest(encodedStr, null, "text/html", null, options);
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from an URL. Return a list of languages.
     * @param url URL for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     *         descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(URL url, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(null, url.toString(), null, null, options);
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Performs language identification on data read from a string. Return a list of languages.
     * @param content String content for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     *         descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content, LanguageOptions options)
            throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, null, options);
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Returns a list of candidate languages in order of descending confidence from a string.
     * @param content String content for language detection.
     * @param unit The unit of content. Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as
     *             one sentence.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by
     *         descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content, InputUnit unit, LanguageOptions options)
            throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, unit, options);
        return sendPostRequest(request, urlBase + LANGUAGE_SERVICE_PATH, LanguageResponse.class);
    }

    /**
     * Returns morphological analysis of the input file.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, InputStream inputStream,
                                            LanguageCode language, MorphologyOptions options)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        MorphologyRequest request = new MorphologyRequest(language, encodedStr, null, "text/html", null, options);
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the URL content.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, URL url, LanguageCode language,
                                            MorphologyOptions options) throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, null, url.toString(), null, null, options);
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content,
                                            LanguageCode language, MorphologyOptions options)
            throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, null, options);
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings.
     * Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content,
                                            LanguageCode language, InputUnit unit, MorphologyOptions options)
            throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, unit, options);
        return sendPostRequest(request, urlBase + MORPHOLOGY_SERVICE_PATH + morphologicalFeature.toString(),
                MorphologyResponse.class);
    }

    /**
     * Returns entities extracted from the input file.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(InputStream inputStream, LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        EntitiesRequest request = new EntitiesRequest(language, encodedStr, null, "text/html", null, options);
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Returns entities extracted from the URL content.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(URL url, LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        EntitiesRequest request = new EntitiesRequest(language, null, url.toString(), null, null, options);
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Returns entities extracted from a string.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(String content, LanguageCode language, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        EntitiesRequest request = new EntitiesRequest(language, content, null, null, null, options);
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Returns entities extracted from a string.
     *
     * The response is a list of extracted entities.
     * Each entity includes chain ID (all instances of the same entity share a chain id),
     * mention (entity text in the input), normalized text (the most complete form of this entity that appears in
     * the input), count (how many times this entity appears in the input), and the confidence associated with the
     * extraction.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Entity options.
     * @return EntitiesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntitiesResponse getEntities(String content, LanguageCode language, InputUnit unit, EntitiesOptions options)
            throws RosetteAPIException, IOException {
        EntitiesRequest request = new EntitiesRequest(language, content, null, null, unit, options);
        return sendPostRequest(request, urlBase + ENTITIES_SERVICE_PATH, EntitiesResponse.class);
    }

    /**
     * Links entities in the input file to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntitiesResponse getLinkedEntities(InputStream inputStream, LanguageCode language)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinkedEntitiesRequest request = new LinkedEntitiesRequest(language, encodedStr, null, "text/html", null);
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Links entities in the URL content to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntitiesResponse getLinkedEntities(URL url, LanguageCode language)
            throws RosetteAPIException, IOException {
        LinkedEntitiesRequest request = new LinkedEntitiesRequest(language, null, url.toString(), null, null);
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntitiesResponse getLinkedEntities(String content, LanguageCode language)
            throws RosetteAPIException, IOException {
        LinkedEntitiesRequest request = new LinkedEntitiesRequest(language, content, null, null, null);
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base (Wikidata).
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity
     * share a chain id), the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntitiesResponse getLinkedEntities(String content, LanguageCode language, InputUnit unit)
            throws RosetteAPIException, IOException {
        LinkedEntitiesRequest request = new LinkedEntitiesRequest(language, content, null, null, unit);
        return sendPostRequest(request, urlBase + ENTITIES_LINKED_SERVICE_PATH, LinkedEntitiesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoriesResponse getCategories(InputStream inputStream, LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        CategoriesRequest request = new CategoriesRequest(language, encodedStr, null, "text/html", null, options);
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the URL content. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoriesResponse getCategories(URL url, LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        CategoriesRequest request = new CategoriesRequest(language, null, url.toString(), null, null, options);
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param options CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoriesResponse getCategories(String content, LanguageCode language, CategoriesOptions options)
            throws RosetteAPIException, IOException {
        CategoriesRequest request = new CategoriesRequest(language, content, null, null, null, options);
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual
     * categories defined in the <a href="http://www.iab.net/QAGInitiative/overview/taxonomy">QAG Taxonomy</a>.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options CategoriesOptions.
     * @return CategoriesResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoriesResponse getCategories(String content, LanguageCode language, InputUnit unit,
                                            CategoriesOptions options)  throws RosetteAPIException, IOException {
        CategoriesRequest request = new CategoriesRequest(language, content, null, null, unit, options);
        return sendPostRequest(request, urlBase + CATEGORIES_SERVICE_PATH, CategoriesResponse.class);
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
    public SentimentResponse getSentiment(InputStream inputStream, LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        SentimentRequest request = new SentimentRequest(language, encodedStr, null, "text/html", null, options);
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
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
    public SentimentResponse getSentiment(URL url, LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, null, url.toString(), null, null, options);
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
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
    public SentimentResponse getSentiment(String content, LanguageCode language, SentimentOptions options)
            throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, null, options);
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content, LanguageCode language, InputUnit unit,
                                          SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, unit, options);
        return sendPostRequest(request, urlBase + SENTIMENT_SERVICE_PATH, SentimentResponse.class);
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
    public TokensResponse getTokens(InputStream inputStream, LanguageCode language)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        MorphologyRequest request = new MorphologyRequest(language, encodedStr, null, "text/html", null, null);
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
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
    public TokensResponse getTokens(URL url, LanguageCode language) throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, null, url.toString(), null, null, null);
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
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
    public TokensResponse getTokens(String content, LanguageCode language) throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, null, null);
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokensResponse getTokens(String content, LanguageCode language, InputUnit unit)
            throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, unit, null);
        return sendPostRequest(request, urlBase + TOKENS_SERVICE_PATH, TokensResponse.class);
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
    public SentencesResponse getSentences(InputStream inputStream, LanguageCode language)
            throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        MorphologyRequest request = new MorphologyRequest(language, encodedStr, null, "text/html", null, null);
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
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
    public SentencesResponse getSentences(URL url, LanguageCode language) throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, null, url.toString(), null, null, null);
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
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
    public SentencesResponse getSentences(String content, LanguageCode language)
            throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, null, null);
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.apimodel.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.apimodel.InputUnit}).
     *             Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentencesResponse getSentences(String content, LanguageCode language, InputUnit unit)
            throws RosetteAPIException, IOException {
        MorphologyRequest request = new MorphologyRequest(language, content, null, null, unit, null);
        return sendPostRequest(request, urlBase + SENTENCES_SERVICE_PATH, SentencesResponse.class);
    }

    /**
     * Provides information on the language endpoint
     *
     * @return {@link com.basistech.rosette.apimodel.LanguageInfoResponse LanguageInfoResponse}
     * @throws RosetteAPIException
     * @throws IOException
     */
    public LanguageInfoResponse getLanguageInfo() throws RosetteAPIException, IOException {
        return sendGetRequest(urlBase + LANGUAGE_SERVICE_PATH + INFO_SERVICE_PATH, LanguageInfoResponse.class);
    }

    /**
     * Provides information on Rosette API
     *
     * @return {@link com.basistech.rosette.apimodel.InfoResponse InfoResponse}
     * @throws RosetteAPIException
     * @throws IOException
     */
    public InfoResponse getInfo() throws RosetteAPIException, IOException {
        return sendGetRequest(urlBase + INFO_SERVICE_PATH, InfoResponse.class);
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
    private <T extends Response> T sendGetRequest(String urlStr, Class<T> clazz)
            throws IOException, RosetteAPIException {
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
    private <T extends Response> T sendPostRequest(Object request, String urlStr, Class<T> clazz)
            throws RosetteAPIException, IOException {
        RosetteAPIException lastException = null;
        int numRetries = this.failureRetries;
        while (numRetries-- > 0) {
            HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
            httpUrlConnection.setRequestMethod("POST");
            try (OutputStream os = httpUrlConnection.getOutputStream()) {
                mapper.writeValue(os, request);
            }
            try {
                return clazz.cast(getResponse(httpUrlConnection, clazz));
            } catch (RosetteAPIException e) {
                // only 5xx errors are worthy retrying, others throw right away
                if (e.getHttpStatusCode() < 500) {
                    throw e;
                } else {
                    lastException =e;
                }
            }
        }
        throw lastException;
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
    private Response getResponse(HttpURLConnection httpUrlConnection, Class<? extends Response> clazz)
            throws IOException, RosetteAPIException {
        int status = httpUrlConnection.getResponseCode();
        String encoding = httpUrlConnection.getContentEncoding();
        try (
            InputStream stream = status != HTTP_OK ?
                    httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
            InputStream inputStream = "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(stream) : stream
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

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
            return buf;
        }
    }
}
