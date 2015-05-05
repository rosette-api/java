package com.basistech.rosette.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.basistech.rosette.model.EntityResponse;
import com.basistech.rosette.model.ErrorResponse;
import com.basistech.rosette.model.LanguageResponse;
import com.basistech.rosette.model.LinkedEntityResponse;
import com.basistech.rosette.model.NameTranslationRequest;
import com.basistech.rosette.model.NameTranslationResponse;
import com.basistech.rosette.model.SentOptions;
import com.basistech.rosette.model.SentimentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.net.HttpURLConnection.HTTP_OK;

public class RosetteAPI {
    private final String key;
//    private String urlBase = "http://localhost:8755/rest/v1/";
    private String urlBase = "https://api.rosette.com/rest/v1/";
    private final String admOutput = "?output=rosette";
    private final String debugOutput = "?debug=true";
    private final static String entities = "entities";
    private final static String entitiesLinked = "entities/linked";
    private final static String language = "language";
    private final static String sentiment = "sentiment";
    private final static String translateName = "translated-name";
    private final ObjectMapper mapper;

    public RosetteAPI(String key) {
        this.key = key;
        mapper = new ObjectMapper();
    }

    public void setServiceUrl(String url) {
        this.urlBase = url;
    }

    public NameTranslationResponse translateName(NameTranslationRequest request) throws RosetteAPIException {
        String jsonStr = sendRequestTranslateName(request, urlBase + translateName);
        try {
            return mapper.readValue(jsonStr, NameTranslationResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String sendRequestTranslateName(NameTranslationRequest request, String urlStr) throws RosetteAPIException {
        String payloadStr = null;
        try {
            payloadStr = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sendRequest(payloadStr, urlStr);
    }

    public LanguageResponse getLanguageFromFile(File file) throws RosetteAPIException {
        String jsonStr = sendRequestFromFileWorker(file, null, urlBase + language);
        try {
            return mapper.readValue(jsonStr, LanguageResponse.class);
        } catch (IOException e) {
            throw new RosetteAPIException(404, new ErrorResponse(null, e.toString(), file.getAbsolutePath()));
        }
    }

    public LanguageResponse getLanguageFromUrl(URL url) throws RosetteAPIException {
        String jsonStr = sendRequestWorker(url, urlBase + language);
        try {
            return mapper.readValue(jsonStr, LanguageResponse.class);
        } catch (IOException e) {
            throw new RosetteAPIException(404, new ErrorResponse(null, e.toString(), url.toString()));
        }
    }

    public LanguageResponse getLanguageFromText(String content) throws RosetteAPIException {
        String jsonStr =  sendRequestWorker(content, urlBase + language);
        try {
            return mapper.readValue(jsonStr, LanguageResponse.class);
        } catch (IOException e) {
            throw new RosetteAPIException(404, new ErrorResponse(null, e.toString(), null));
        }
    }

    public EntityResponse getEntityFromFile(File file) throws RosetteAPIException, IOException {
        String jsonStr =  sendRequestFromFileWorker(file, null, urlBase + entities);
        return mapper.readValue(jsonStr, EntityResponse.class);
    }

    public EntityResponse getEntityFromUrl(URL url) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestWorker(url, urlBase + entities);
        return mapper.readValue(jsonStr, EntityResponse.class);
    }

    public EntityResponse getEntityFromText(String content) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestWorker(content, urlBase + entities);
        return mapper.readValue(jsonStr, EntityResponse.class);
    }

    public LinkedEntityResponse getResolvedEntityFromFile(File file) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestFromFileWorker(file, null, urlBase + entitiesLinked);
        return mapper.readValue(jsonStr, LinkedEntityResponse.class);
    }

    public LinkedEntityResponse getResolvedEntityFromUrl(URL url) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestWorker(url, urlBase + entitiesLinked);
        return mapper.readValue(jsonStr, LinkedEntityResponse.class);
    }

    public LinkedEntityResponse getResolvedEntityFromText(String content) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestWorker(content, urlBase + entitiesLinked);
        return mapper.readValue(jsonStr, LinkedEntityResponse.class);
    }

    public SentimentResponse getSentimentFromFile(File file) throws RosetteAPIException, IOException {
        String jsonStr = sendRequestFromFileWorker(file, null, urlBase + sentiment);
        return mapper.readValue(jsonStr, SentimentResponse.class);
    }

    public SentimentResponse getSentimentFromFile(File file, SentOptions options) throws RosetteAPIException, IOException {
        String optionsStr = mapper.writeValueAsString(options);
        String jsonStr = sendRequestFromFileWorker(file, optionsStr, urlBase + sentiment);
        return mapper.readValue(jsonStr, SentimentResponse.class);
    }

    public SentimentResponse getSentimentFromUrl(URL url, SentOptions options) throws RosetteAPIException, IOException {
        String optionsStr = mapper.writeValueAsString(options);
        String jsonStr = sendRequestWorker(url, optionsStr, urlBase + sentiment);
        return mapper.readValue(jsonStr, SentimentResponse.class);
    }

    public SentimentResponse getSentimentFromText(String content, SentOptions options) throws RosetteAPIException, IOException {
        String optionsStr = mapper.writeValueAsString(options);
        String jsonStr = sendRequestWorker(content, optionsStr, urlBase + sentiment);
        return mapper.readValue(jsonStr, SentimentResponse.class);
    }

    private String sendRequestFromFileWorker(File file, String options, String url) throws RosetteAPIException {
        try {
            MultipartUtility multipartUtility = new MultipartUtility(url + debugOutput, "UTF-8", key);
            multipartUtility.addFilePart("content", file);
            if (options != null && options.length() > 0) {
                String optionsStr = "{\"options\":";
                optionsStr += options;
                optionsStr += "}";
                InputStream inputStream = new ByteArrayInputStream(optionsStr.getBytes(StandardCharsets.UTF_8));
                multipartUtility.addFilePart("options", inputStream, "application/json");
            }

            List<String> response = multipartUtility.finish();

            if (response.size() > 0) {
                return response.get(0).toString();
            } else {
                return "";
            }
        } catch (IOException e) {
            throw new RosetteAPIException(404, new ErrorResponse(null, e.getMessage(), file.getAbsolutePath()));
        }
    }

    private String sendRequestWorker(URL url, String serviceUrl) throws RosetteAPIException {
        return sendRequestWorker(url, null, serviceUrl);
    }

    private String sendRequestWorker(URL url, String options, String serviceUrl) throws RosetteAPIException {
        String payloadStr = "{";
        payloadStr += "\"contentUri\":\"" + url + "\"";
        if (options != null && options.length() != 0) {
            payloadStr += ", \"options\":" + options;
        }
        payloadStr += "}";
        return sendRequest(payloadStr, serviceUrl);
    }

    private String sendRequestWorker(String content, String serviceUrl) throws RosetteAPIException {
        return sendRequestWorker(content, null, serviceUrl);
    }

    private String sendRequestWorker(String content, String options, String serviceUrl) throws RosetteAPIException {
        String payloadStr = "{";
        payloadStr += "\"content\":\"" + content + "\"";
        if (options != null && options.length() != 0) {
            payloadStr += ", \"options\":" + options;
        }
        payloadStr += "}";
        return sendRequest(payloadStr, serviceUrl);
    }

    private String sendRequest(String payloadStr, String urlStr) throws RosetteAPIException {
        String jsonStr = "";
        try {
            URL url = new URL(urlStr + debugOutput);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("user_key", key);
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty("Accept", "application/json");
            httpUrlConnection.connect();

            byte[] payload = payloadStr.getBytes();
            OutputStream os  = httpUrlConnection.getOutputStream();
            os.write(payload);
            os.close();

            InputStream inputStream = httpUrlConnection.getResponseCode() != HTTP_OK ?
                    httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();

            DataInputStream istream = new DataInputStream(inputStream);
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);

                jsonStr = response.toString();
                int code = httpUrlConnection.getResponseCode();
                if (HTTP_OK != code) {
                    try {
                        ErrorResponse errorResponse = mapper.readValue(jsonStr, ErrorResponse.class);
                        throw new RosetteAPIException(code, errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

}
