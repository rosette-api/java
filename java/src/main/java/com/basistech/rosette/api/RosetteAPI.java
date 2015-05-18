package com.basistech.rosette.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.xml.bind.DatatypeConverter;

import com.basistech.rosette.model.CategoryOptions;
import com.basistech.rosette.model.CategoryRequest;
import com.basistech.rosette.model.CategoryResponse;
import com.basistech.rosette.model.ConstantsResponse;
import com.basistech.rosette.model.EntityOptions;
import com.basistech.rosette.model.EntityRequest;
import com.basistech.rosette.model.EntityResponse;
import com.basistech.rosette.model.ErrorResponse;
import com.basistech.rosette.model.InfoResponse;
import com.basistech.rosette.model.InputUnit;
import com.basistech.rosette.model.LanguageInfoResponse;
import com.basistech.rosette.model.LanguageOptions;
import com.basistech.rosette.model.LanguageRequest;
import com.basistech.rosette.model.LanguageResponse;
import com.basistech.rosette.model.LinguisticsOptions;
import com.basistech.rosette.model.LinguisticsRequest;
import com.basistech.rosette.model.LinkedEntityOption;
import com.basistech.rosette.model.LinkedEntityRequest;
import com.basistech.rosette.model.LinkedEntityResponse;
import com.basistech.rosette.model.MorphologyResponse;
import com.basistech.rosette.model.NameMatcherRequest;
import com.basistech.rosette.model.NameMatcherResponse;
import com.basistech.rosette.model.NameTranslationRequest;
import com.basistech.rosette.model.NameTranslationResponse;
import com.basistech.rosette.model.PingResponse;
import com.basistech.rosette.model.Response;
import com.basistech.rosette.model.SentenceResponse;
import com.basistech.rosette.model.SentimentOptions;
import com.basistech.rosette.model.SentimentRequest;
import com.basistech.rosette.model.SentimentResponse;
import com.basistech.rosette.model.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * You can use the RosetteAPI to access Rosette API endpoints, which can analyze, summarize, and classify large amounts
 * of unstructured text.
 */
public class RosetteAPI {
    public static final String DEFAULT_URL_BASE = "https://api.rosette.com/rest/v1/";

    private String key;
    private String urlBase = DEFAULT_URL_BASE;
    private String debugOutput = "";
    private final static String languageServicePath = "language";
    private final static String morphologyServicePath = "morphology/";
    private final static String entitiesServicePath = "entities";
    private final static String entitiesLinkedServicePath = "entities/linked";
    private final static String categoriesServicePath = "categories";
    private final static String sentimentServicePath = "sentiment";
    private final static String translatedNameServicePath = "translated-name";
    private final static String matchedNameServicePath = "matched-name";
    private final static String tokensServicePath = "tokens";
    private final static String sentencesServicePath = "sentences";
    private final static String infoServicePath = "info";
    private final static String pingServicePath = "ping";
    private final static String DEBUG_PARAM_ON = "?debug=true";
    private final static String DEBUG_PARAM_OFF = "";
    private final ObjectMapper mapper;

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
        ENTITIES("Entities", entitiesServicePath),
        LINKED_ENTITIES("Linked Entities", entitiesLinkedServicePath),
        CATEGORIES("Categories", categoriesServicePath),
        SENTIMENT("Sentiment", sentimentServicePath),
        TRANSLATED_NAME("Translated Name", translatedNameServicePath),
        MATCHED_NAME("Matched Name", matchedNameServicePath);

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
        mapper = new ObjectMapper();
    }

    /**
     * Constructs a Rosette API instance, using ROSETTE_API_KEY environment variable as key.
     */
    public RosetteAPI() {
        this.key = System.getenv("ROSETTE_API_KEY");
        mapper = new ObjectMapper();
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
     * Gets information about the Rosette API.
     * @return InfoResponse
     * @throws IOException
     * @throws RosetteAPIException
     */
    public InfoResponse info() throws IOException, RosetteAPIException {
        return (InfoResponse) sendGetRequest(urlBase + infoServicePath, InfoResponse.class);
    }

    /**
     * Pings the Rosette API.
     * @return PingResponse
     * @throws IOException
     * @throws RosetteAPIException
     */
    public PingResponse ping() throws IOException, RosetteAPIException {
        return (PingResponse) sendGetRequest(urlBase + pingServicePath, PingResponse.class);
    }

    /**
     * Matches 2 names and returns a score in NameMatcherResponse.
     * @param request NameMatcherRequest contains 2 names.
     * @return NameMatcherResponse
     * @throws RosetteAPIException
     * @throws IOException
     */
    public NameMatcherResponse matchName(NameMatcherRequest request) throws RosetteAPIException, IOException {
        return (NameMatcherResponse) sendRequest(request, urlBase + matchedNameServicePath, NameMatcherResponse.class);
    }

    /**
     * Translates a name into the target language specified in NameTranslationRequest.
     * @param request NameTranslationRequest contains the name to be translated and the target language.
     * @return NameTranslationResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public NameTranslationResponse translateName(NameTranslationRequest request) throws RosetteAPIException, IOException {
        return (NameTranslationResponse) sendRequest(request, urlBase + translatedNameServicePath, NameTranslationResponse.class);
    }

    /**
     * Returns a list of candidate languages in order of descending confidence from an InputStream.
     * @param inputStream Input stream of file.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(InputStream inputStream, LanguageOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LanguageRequest request = new LanguageRequest(encodedStr, null, "text/html", null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    /**
     * Returns a list of candidate languages in order of descending confidence from an URL.
     * @param url URL for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(URL url, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(null, url.toString(), null, null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    /**
     * Returns a list of candidate languages in order of descending confidence from a string.
     * @param content String content for language detection.
     * @param options Options to Language API.
     * @return An ordered list of detected languages, including language and detection confidence, sorted by descending confidence.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LanguageResponse getLanguage(String content, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
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
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    /**
     * Returns morphological analysis of the input file.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, InputStream inputStream, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of the URL content.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, URL url, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath + morphologicalFeature.toString(), MorphologyResponse.class);
    }

    /**
     * Returns morphological analysis of a string.
     * The response may include lemmas, part of speech tags, compound word components, and Han readings. Support for specific return types depends on language.
     * @param morphologicalFeature Type of morphological analysis to perform.
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Linguistics options
     * @return MorphologyResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public MorphologyResponse getMorphology(MorphologicalFeature morphologicalFeature, String content, String language, InputUnit unit, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath + morphologicalFeature.toString(), MorphologyResponse.class);
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
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(InputStream inputStream, String language, EntityOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        EntityRequest request = new EntityRequest(language, encodedStr, null, "text/html", null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
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
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(URL url, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, null, url.toString(), null, null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
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
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(String content, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
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
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options Entity options.
     * @return EntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public EntityResponse getEntity(String content, String language, InputUnit unit, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, unit, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
    }

    /**
     * Links entities in the input file to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options LinkedEntity options.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(InputStream inputStream, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinkedEntityRequest request = new LinkedEntityRequest(language, encodedStr, null, "text/html", null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    /**
     * Links entities in the URL content to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options LinkedEntity options.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(URL url, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, null, url.toString(), null, null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options LinkedEntity options.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(String content, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    /**
     * Links entities in a string to entities in the knowledge base.
     * The response identifies the entities in the input that have been linked to entities in the knowledge base.
     * Each entity includes an entity id (from the knowledge base), a chain id (all instances of the same entity share a chain id),
     * the mention (entity text from the input), and confidence associated with the linking.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options LinkedEntity options.
     * @return LinkedEntityResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public LinkedEntityResponse getLinkedEntity(String content, String language, InputUnit unit, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, unit, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the input file. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(InputStream inputStream, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        CategoryRequest request = new CategoryRequest(language, encodedStr, null, "text/html", null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in the URL content. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(URL url, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, null, url.toString(), null, null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(String content, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    /**
     * Returns an ordered list of categories identified in a string. The categories are Tier 1 contextual categories defined in the QAG Taxonomy.
     *
     * The response is the contextual categories identified in the input.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options CategoryOptions.
     * @return CategoryResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public CategoryResponse getCategories(String content, String language, InputUnit unit, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, unit, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(InputStream inputStream, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        SentimentRequest request = new SentimentRequest(language, encodedStr, null, "text/html", null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(URL url, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, null, url.toString(), null, null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    /**
     * Analyzes the positive and negative sentiment expressed by the input.
     *
     * The response contains sentiment analysis results.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @param options SentimentOptions.
     * @return SentimentResponse
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentimentResponse getSentiment(String content, String language, InputUnit unit, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, unit, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(InputStream inputStream, String language) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, null);
        return (TokenResponse) sendRequest(request, urlBase + tokensServicePath, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(URL url, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, null);
        return (TokenResponse) sendRequest(request, urlBase + tokensServicePath, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(String content, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, null);
        return (TokenResponse) sendRequest(request, urlBase + tokensServicePath, TokenResponse.class);
    }

    /**
     * Divides the input into tokens.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of tokens.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public TokenResponse getTokens(String content, String language, InputUnit unit) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, null);
        return (TokenResponse) sendRequest(request, urlBase + tokensServicePath, TokenResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param inputStream Input stream of file.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(InputStream inputStream, String language) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, null);
        return (SentenceResponse) sendRequest(request, urlBase + sentencesServicePath, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param url URL containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(URL url, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, null);
        return (SentenceResponse) sendRequest(request, urlBase + sentencesServicePath, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(String content, String language) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, null);
        return (SentenceResponse) sendRequest(request, urlBase + sentencesServicePath, SentenceResponse.class);
    }

    /**
     * Divides the input into sentences.
     *
     * @param content String containing the data.
     * @param language Language of input if known (see {@link com.basistech.rosette.model.LanguageCode}), or null.
     * @param unit The unit of content (see {@link com.basistech.rosette.model.InputUnit}). Can be SENTENCE or DOC. If SENTENCE, the entire content is treated as one sentence.
     * @return The response contains a list of sentences.
     * @throws RosetteAPIException - If there is a problem with the Rosette API request.
     * @throws IOException - If there is a communication or JSON serialization/deserialization error.
     */
    public SentenceResponse getSentences(String content, String language, InputUnit unit) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, null);
        return (SentenceResponse) sendRequest(request, urlBase + sentencesServicePath, SentenceResponse.class);
    }

    public LanguageInfoResponse getLanguageInfo() throws RosetteAPIException, IOException {
        return (LanguageInfoResponse) sendGetRequest(urlBase + languageServicePath + "/" + infoServicePath, LanguageInfoResponse.class);
    }

    public ConstantsResponse getInfo(EndpointInfo endpointInfo) throws RosetteAPIException, IOException {
        return (ConstantsResponse) sendGetRequest(urlBase + endpointInfo.getServicePath() + "/" + infoServicePath, ConstantsResponse.class);
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
    private Response sendGetRequest(String urlStr, Class<? extends Response> clazz) throws IOException, RosetteAPIException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("GET");
        return getResponse(httpUrlConnection, clazz);
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
    private Response sendRequest(Object request, String urlStr, Class<? extends Response> clazz) throws RosetteAPIException, IOException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("POST");
        try (OutputStream os = httpUrlConnection.getOutputStream()) {
            mapper.writeValue(os, request);
        }
        return getResponse(httpUrlConnection, clazz);
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
                ("gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(stream) : stream)
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
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString().getBytes();
    }
}
