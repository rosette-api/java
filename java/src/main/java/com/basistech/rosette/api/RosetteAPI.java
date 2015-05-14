package com.basistech.rosette.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.xml.bind.DatatypeConverter;

import com.basistech.rosette.model.CategoryOptions;
import com.basistech.rosette.model.CategoryRequest;
import com.basistech.rosette.model.CategoryResponse;
import com.basistech.rosette.model.EntityOptions;
import com.basistech.rosette.model.EntityRequest;
import com.basistech.rosette.model.EntityResponse;
import com.basistech.rosette.model.ErrorResponse;
import com.basistech.rosette.model.InfoResponse;
import com.basistech.rosette.model.InputUnit;
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
import com.basistech.rosette.model.Request;
import com.basistech.rosette.model.Response;
import com.basistech.rosette.model.SentimentOptions;
import com.basistech.rosette.model.SentimentRequest;
import com.basistech.rosette.model.SentimentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.net.HttpURLConnection.HTTP_OK;

public class RosetteAPI {

    private String key;
    private String urlBase = "https://api.rosette.com/rest/v1/";
    private String debugOutput = "";
    private final static String languageServicePath = "language";
    private final static String morphologyServicePath = "morphology/complete";
    private final static String entitiesServicePath = "entities";
    private final static String entitiesLinkedServicePath = "entities/linked";
    private final static String categoriesServicePath = "categories";
    private final static String sentimentServicePath = "sentiment";
    private final static String translatedNameServicePath = "translated-name";
    private final static String matchedNameServicePath = "matched-name";
    private final static String infoServicePath = "info";
    private final static String pingServicePath = "ping";
    private final ObjectMapper mapper;

    public RosetteAPI(String key) {
        this.key = key;
        mapper = new ObjectMapper();
    }

    public RosetteAPI() {
        this.key = System.getenv("ROSETTE_API_KEY");
        mapper = new ObjectMapper();
    }

    public void setServiceUrl(String url) {
        if (!url.endsWith("/")) {
            urlBase = url + "/";
        }
    }

    public void setAPIKey(String key) {
        this.key = key;
    }

    public void setDebugOutput(boolean flag) {
        this.debugOutput = flag ? "?debug=true" : "";
    }

    public InfoResponse getInfo() throws IOException, RosetteAPIException {
        return (InfoResponse) sendGetRequest(urlBase + infoServicePath, InfoResponse.class);
    }

    public PingResponse getPing() throws IOException, RosetteAPIException {
        return (PingResponse) sendGetRequest(urlBase + pingServicePath, PingResponse.class);
    }

    public NameMatcherResponse matchName(NameMatcherRequest request) throws RosetteAPIException, IOException {
        return (NameMatcherResponse) sendRequest(request, urlBase + matchedNameServicePath, NameMatcherResponse.class);
    }

    public NameTranslationResponse translateName(NameTranslationRequest request) throws RosetteAPIException, IOException {
        return (NameTranslationResponse) sendRequest(request, urlBase + translatedNameServicePath, NameTranslationResponse.class);
    }

    public LanguageResponse getLanguage(InputStream inputStream, LanguageOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LanguageRequest request = new LanguageRequest(encodedStr, null, "text/html", null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    public LanguageResponse getLanguage(URL url, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(null, url.toString(), null, null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    public LanguageResponse getLanguage(String content, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, null, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    public LanguageResponse getLanguage(String content, InputUnit unit, LanguageOptions options) throws RosetteAPIException, IOException {
        LanguageRequest request = new LanguageRequest(content, null, null, unit, options);
        return (LanguageResponse) sendRequest(request, urlBase + languageServicePath, LanguageResponse.class);
    }

    public MorphologyResponse getMorphology(InputStream inputStream, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinguisticsRequest request = new LinguisticsRequest(language, encodedStr, null, "text/html", null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath, MorphologyResponse.class);
    }

    public MorphologyResponse getMorphology(URL url, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, null, url.toString(), null, null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath, MorphologyResponse.class);
    }

    public MorphologyResponse getMorphology(String content, String language, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, null, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath, MorphologyResponse.class);
    }

    public MorphologyResponse getMorphology(String content, String language, InputUnit unit, LinguisticsOptions options) throws RosetteAPIException, IOException {
        LinguisticsRequest request = new LinguisticsRequest(language, content, null, null, unit, options);
        return (MorphologyResponse) sendRequest(request, urlBase + morphologyServicePath, MorphologyResponse.class);
    }

    public EntityResponse getEntity(InputStream inputStream, String language, EntityOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        EntityRequest request = new EntityRequest(language, encodedStr, null, "text/html", null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
    }

    public EntityResponse getEntity(URL url, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, null, url.toString(), null, null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
    }

    public EntityResponse getEntity(String content, String language, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, null, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
    }

    public EntityResponse getEntity(String content, String language, InputUnit unit, EntityOptions options) throws RosetteAPIException, IOException {
        EntityRequest request = new EntityRequest(language, content, null, null, unit, options);
        return (EntityResponse) sendRequest(request, urlBase + entitiesServicePath, EntityResponse.class);
    }

    public LinkedEntityResponse getLinkedEntity(InputStream inputStream, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        LinkedEntityRequest request = new LinkedEntityRequest(language, encodedStr, null, "text/html", null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    public LinkedEntityResponse getLinkedEntity(URL url, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, null, url.toString(), null, null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    public LinkedEntityResponse getLinkedEntity(String content, String language, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, null, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    public LinkedEntityResponse getLinkedEntity(String content, String language, InputUnit unit, LinkedEntityOption options) throws RosetteAPIException, IOException {
        LinkedEntityRequest request = new LinkedEntityRequest(language, content, null, null, unit, options);
        return (LinkedEntityResponse) sendRequest(request, urlBase + entitiesLinkedServicePath, LinkedEntityResponse.class);
    }

    public CategoryResponse getCategories(InputStream inputStream, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        CategoryRequest request = new CategoryRequest(language, encodedStr, null, "text/html", null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    public CategoryResponse getCategories(URL url, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, null, url.toString(), null, null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    public CategoryResponse getCategories(String content, String language, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, null, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    public CategoryResponse getCategories(String content, String language, InputUnit unit, CategoryOptions options)  throws RosetteAPIException, IOException {
        CategoryRequest request = new CategoryRequest(language, content, null, null, unit, options);
        return (CategoryResponse) sendRequest(request, urlBase + categoriesServicePath, CategoryResponse.class);
    }

    public SentimentResponse getSentiment(InputStream inputStream, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        String encodedStr = DatatypeConverter.printBase64Binary(getBytes(inputStream));
        SentimentRequest request = new SentimentRequest(language, encodedStr, null, "text/html", null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    public SentimentResponse getSentiment(URL url, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, null, url.toString(), null, null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    public SentimentResponse getSentiment(String content, String language, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, null, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    public SentimentResponse getSentiment(String content, String language, InputUnit unit, SentimentOptions options) throws RosetteAPIException, IOException {
        SentimentRequest request = new SentimentRequest(language, content, null, null, unit, options);
        return (SentimentResponse) sendRequest(request, urlBase + sentimentServicePath, SentimentResponse.class);
    }

    private Response sendGetRequest(String urlStr, Class<? extends Response> clazz) throws IOException, RosetteAPIException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("GET");
        return getResponse(httpUrlConnection, clazz);
    }

    private Response sendRequest(Request request, String urlStr, Class<? extends Response> clazz) throws RosetteAPIException, IOException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os = httpUrlConnection.getOutputStream();
        mapper.writeValue(os, request);
        os.close();
        return getResponse(httpUrlConnection, clazz);
    }

    private Response sendRequest(NameMatcherRequest request, String urlStr, Class<? extends Response> clazz) throws RosetteAPIException, IOException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("POST");
        DataOutputStream os  = new DataOutputStream(httpUrlConnection.getOutputStream());
        mapper.writeValue(os, request);
        os.close();
        return getResponse(httpUrlConnection, clazz);
    }

    private Response sendRequest(NameTranslationRequest request, String urlStr, Class<? extends Response> clazz) throws RosetteAPIException, IOException {
        HttpURLConnection httpUrlConnection = openHttpURLConnection(urlStr);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os  = httpUrlConnection.getOutputStream();
        mapper.writeValue(os, request);
        os.close();
        return getResponse(httpUrlConnection, clazz);
    }

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
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        return httpUrlConnection;
    }

    private Response getResponse(HttpURLConnection httpUrlConnection, Class<? extends Response> clazz) throws IOException, RosetteAPIException {
        int status = httpUrlConnection.getResponseCode();
        String encoding = httpUrlConnection.getContentEncoding();
        InputStream stream = status != HTTP_OK ? httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
        try (
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

    private static byte[] getBytes(InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
        }
        return buf;
    }
}
