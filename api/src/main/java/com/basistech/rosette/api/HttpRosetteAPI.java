/*
* Copyright 2016 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.AdmRequest;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.apimodel.jackson.DocumentRequestMixin;
import com.basistech.rosette.dm.AnnotatedText;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.io.ByteStreams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Access to the RosetteAPI via HTTP.
 */
public class HttpRosetteAPI extends AbstractRosetteAPI {

    public static final String DEFAULT_URL_BASE = "https://api.rosette.com/rest/v1";
    public static final String SERVICE_NAME = "RosetteAPI";
    public static final String BINDING_VERSION = getVersion();
    public static final String USER_AGENT_STR = SERVICE_NAME + "-Java/" + BINDING_VERSION;
    private static final Logger LOG = LoggerFactory.getLogger(HttpRosetteAPI.class);
    private String urlBase = DEFAULT_URL_BASE;
    private int failureRetries = 1;
    private ObjectMapper mapper;
    private CloseableHttpClient httpClient;
    private List<Header> additionalHeaders;
    private int connectionConcurrency = 2;
    private boolean closeClientOnClose = true;

    private HttpRosetteAPI() {
        // use builder
    }

    /**
     * Constructs a Rosette API instance using the builder syntax.
     *
     * @param key            Rosette API key. This may be null for use with an on-premise deployment
     *                     of the Rosette API.
     * @param urlToCall   Alternate Rosette API URL. {@code null} uses the default, public, URL.
     * @param failureRetries Number of times to retry in case of failure; {@code null} uses the
     *                       default value: 1.
     * @param connectionConcurrency Number of concurrent connections. Pass this if have subscribed
     *                              to a plan that supports enhanced concurrency, or if you are using
     *                              an on-premise deployment of the Rosette API. {@code null} uses the
     *                              default value: 2.
     * @throws HttpRosetteAPIException  Problem with the API request
     */
    HttpRosetteAPI(String key, String urlToCall, Integer failureRetries,
                       CloseableHttpClient httpClient, List<Header> additionalHeaders,
                       Integer connectionConcurrency, boolean onlyAcceptKnownFields) throws HttpRosetteAPIException {
        urlBase = urlToCall == null ? urlBase : urlToCall.trim().replaceAll("/+$", "");
        if (failureRetries != null && failureRetries >= 1) {
            this.failureRetries = failureRetries;
        }

        if (connectionConcurrency != null) {
            this.connectionConcurrency = connectionConcurrency;
        }

        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, onlyAcceptKnownFields);
        if (httpClient == null) {
            initClient(key, additionalHeaders);
        } else {
            this.httpClient = httpClient;
            initHeaders(key, additionalHeaders);
            closeClientOnClose = false;
        }
    }

    /**
     * Returns the version of the binding.
     *
     * @return version of the binding
     */
    private static String getVersion() {
        Properties properties = new Properties();
        try (InputStream ins = HttpRosetteAPI.class.getClassLoader().getResourceAsStream("version.properties")) {
            properties.load(ins);
        } catch (IOException e) {
            // should not happen
        }
        return properties.getProperty("version", "undefined");
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

    private void initClient(String key, List<Header> additionalHeaders) {
        HttpClientBuilder builder = HttpClients.custom();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(connectionConcurrency);
        builder.setConnectionManager(cm);

        initHeaders(key, additionalHeaders);
        builder.setDefaultHeaders(this.additionalHeaders);

        httpClient = builder.build();
        this.additionalHeaders = new ArrayList<>();
    }

    private void initHeaders(String key, List<Header> additionalHeaders) {
        this.additionalHeaders = new ArrayList<>();
        this.additionalHeaders.add(new BasicHeader(HttpHeaders.USER_AGENT, USER_AGENT_STR));
        this.additionalHeaders.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip"));
        if (key != null) {
            this.additionalHeaders.add(new BasicHeader("X-RosetteAPI-Key", key));
            this.additionalHeaders.add(new BasicHeader("X-RosetteAPI-Binding", "java"));
            this.additionalHeaders.add(new BasicHeader("X-RosetteAPI-Binding-Version", BINDING_VERSION));
        }
        if (additionalHeaders != null) {
            this.additionalHeaders.addAll(additionalHeaders);
        }
    }

    /**
     * Return failure retries.
     *
     * @return failure retries
     */
    public int getFailureRetries() {
        return failureRetries;
    }

    /**
     * Gets information about the Rosette API, returns name, version, build number and build time.
     *
     * @return InfoResponse
     * @throws HttpRosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public InfoResponse info() throws IOException, HttpRosetteAPIException {
        return sendGetRequest(urlBase + INFO_SERVICE_PATH, InfoResponse.class);
    }

    /**
     * Pings the Rosette API for a response indicating that the service is available.
     *
     * @return PingResponse
     * @throws HttpRosetteAPIException Rosette specific exception
     * @throws IOException         General IO exception
     */
    public PingResponse ping() throws IOException, HttpRosetteAPIException {
        return sendGetRequest(urlBase + PING_SERVICE_PATH, PingResponse.class);
    }

    /**
     *
     * @param endpoint which endpoint.
     * @param request the data for the request.
     * @param responseClass the Java {@link Class} object for the response object.
     * @param <RequestType> the type of the request object.
     * @param <ResponseType> the type of the response object.
     * @return the response.
     * @throws HttpRosetteAPIException for an error returned from the Rosette API.
     * @throws RosetteRuntimeException for other errors, such as communications problems with HTTP.
     */
    @Override
    public <RequestType extends Request, ResponseType extends Response> ResponseType perform(String endpoint, RequestType request, Class<ResponseType> responseClass) throws HttpRosetteAPIException {
        try {
            return sendPostRequest(request, urlBase + endpoint, responseClass);
        } catch (IOException e) {
            throw new RosetteRuntimeException("IO Exception communicating with the Rosette API", e);
        } catch (URISyntaxException e) {
            throw new RosetteRuntimeException("Invalid URI", e);
        }
    }

    /**
     *
     * @param endpoint which endpoint.
     * @param request the data for the request.
     * @param <RequestType> the type of the request object.
     * @return the response, {@link com.basistech.rosette.dm.AnnotatedText}.
     * @throws HttpRosetteAPIException for an error returned from the Rosette API.
     * @throws RosetteRuntimeException for other errors, such as communications problems with HTTP.
     */
    @Override
    public <RequestType extends Request> AnnotatedText perform(String endpoint, RequestType request) throws HttpRosetteAPIException {
        try {
            return sendPostRequest(request, urlBase + endpoint, AnnotatedText.class);
        } catch (IOException e) {
            throw new RosetteRuntimeException("IO Exception communicating with the Rosette API", e);
        } catch (URISyntaxException e) {
            throw new RosetteRuntimeException("Invalid URI", e);
        }
    }

    /**
     * This method always throws UnsupportedOperationException.
     */
    @Override
    public <RequestType extends Request, ResponseType extends Response> Future<ResponseType> performAsync(String endpoint, RequestType request, Class<ResponseType> responseClass) throws HttpRosetteAPIException {
        throw new UnsupportedOperationException("Asynchronous operations are not yet supported");
    }

    /**
     * Sends a GET request to Rosette API.
     * <p>
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz  Response class
     * @return Response
     * @throws HttpRosetteAPIException
     */
    private <T extends Response> T sendGetRequest(String urlStr, Class<T> clazz) throws HttpRosetteAPIException {
        HttpGet get = new HttpGet(urlStr);
        for (Header header : additionalHeaders) {
            get.addHeader(header);
        }

        try (CloseableHttpResponse httpResponse = httpClient.execute(get)) {
            T resp = getResponse(httpResponse, clazz);
            responseHeadersToExtendedInformation(resp, httpResponse);
            return resp;
        } catch (IOException e) {
            throw new RosetteRuntimeException("IO Exception communicating with the Rosette API", e);
        }
    }

    /**
     * Sends a POST request to Rosette API.
     * <p>
     * Returns a Response.
     *
     * @param urlStr Rosette API end point.
     * @param clazz  Response class
     * @return Response
     * @throws IOException
     */
    private <T> T sendPostRequest(Object request, String urlStr, Class<T> clazz) throws IOException, URISyntaxException {
        ObjectWriter writer = mapper.writer().without(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        boolean notPlainText = false;
        if (request instanceof DocumentRequest) {
            Object rawContent = ((DocumentRequest) request).getRawContent();
            if (rawContent instanceof String) {
                writer = writer.withView(DocumentRequestMixin.Views.Content.class);
            } else if (rawContent != null) {
                notPlainText = true;
            }
        } else if (request instanceof AdmRequest) {
            notPlainText = true;
        }

        URIBuilder uriBuilder = new URIBuilder(urlStr);

        if (AnnotatedText.class.isAssignableFrom(clazz)) {
            //TODO: change output=rosette to Accept: model/vnd.rosette.annotated-data-model header
            uriBuilder.addParameter("output", "rosette");
        }

        final ObjectWriter finalWriter = writer;

        HttpPost post = new HttpPost(uriBuilder.build());
        for (Header header : additionalHeaders) {
            post.addHeader(header);
        }

        //TODO: add compression!
        if (notPlainText) {
            setupMultipartRequest((Request) request, finalWriter, post);
        } else {
            setupPlainRequest(request, finalWriter, post);
        }

        HttpRosetteAPIException lastException = null;
        int numRetries = this.failureRetries;
        while (numRetries-- > 0) {
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                T resp = getResponse(response, clazz);
                Header ridHeader = response.getFirstHeader("X-RosetteAPI-DocumentRequest-Id");
                if (ridHeader != null && ridHeader.getValue() != null) {
                    LOG.debug("DocumentRequest ID " + ridHeader.getValue());
                }
                if (resp instanceof Response) {
                    responseHeadersToExtendedInformation((Response)resp, response);
                }
                return resp;
            } catch (HttpRosetteAPIException e) {
                // only 5xx errors are worthy retrying, others throw right away
                if (e.getHttpStatusCode() < 500) {
                    throw e;
                } else {
                    lastException = e;
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

    private void setupMultipartRequest(final Request request, final ObjectWriter finalWriter, HttpPost post) throws IOException {
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

        AbstractContentBody insBody;
        if (request instanceof DocumentRequest) {
            DocumentRequest docReq = (DocumentRequest) request;
            insBody = new InputStreamBody(docReq.getContentBytes(), ContentType.parse(docReq.getContentType()));
        } else if (request instanceof AdmRequest) {
            //TODO: smile?
            AdmRequest admReq = (AdmRequest) request;
            ObjectWriter writer = mapper.writer().without(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
            byte[] json = writer.writeValueAsBytes(admReq.getText());
            insBody = new ByteArrayBody(json, ContentType.parse(AdmRequest.ADM_CONTENT_TYPE), null);
        } else {
            throw new UnsupportedOperationException("Unsupported request type for multipart processing");
        }
        partBuilder = FormBodyPartBuilder.create("content", insBody);
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
     */
    private <T extends Object> T getResponse(HttpResponse httpResponse, Class<T> clazz) throws IOException, HttpRosetteAPIException {
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
                    if (429 == status) {
                        String concurrencyMessage = "You have exceeded your plan's limit on concurrent calls. "
                                + "This could be caused by multiple processes or threads making Rosette API calls in parallel, "
                                + "or if your httpClient is configured with higher concurrency than your plan allows.";
                        if (emHeader == null) {
                            emHeader = concurrencyMessage;
                        } else {
                            emHeader = concurrencyMessage + System.lineSeparator() + emHeader;
                        }
                    }
                    if (emHeader != null) {
                        errorResponse.setMessage(emHeader);
                    }
                    throw new HttpRosetteAPIException(errorResponse, status);
                } else {
                    String errorContent;
                    if (inputStream != null) {
                        byte[] content = getBytes(inputStream);
                        errorContent = new String(content, "utf-8");
                    } else {
                        errorContent = "(no body)";
                    }
                    // something not from us at all
                    throw new HttpRosetteAPIException("Invalid error response (not json)", new ErrorResponse("invalidErrorResponse", errorContent), status);
                }
            } else {
                return mapper.readValue(inputStream, clazz);
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (closeClientOnClose) {
            httpClient.close();
        }
    }

    /**
     * Builder for HttpRosetteAPI objects.
     */
    public static class Builder {
        private String key;
        private String url;
        private Integer failureRetries;
        private Integer concurrency;
        private CloseableHttpClient httpClient;
        private List<Header> additionalHeaders = new ArrayList<>();
        private boolean onlyAcceptKnownFields;

        /**
         * Specify the API key. This is required for use with the public API, and
         * not necessary with on-premise deployments.
         * @param key the key string.
         * @return this.
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Specify the URL for the service. This is required for use with on-premise
         * deployments, but should not be called for uses of the public API.
         * @param url the URL.
         * @return this.
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * How many times to retry 5xx errors from the service. Some 5xx errors
         * result from transient infrastructure problems.
         * @param failureRetries the number of retries. The default is 1.
         * @return this.
         */
        public Builder failureRetries(Integer failureRetries) {
            this.failureRetries = failureRetries;
            return this;
        }

        /**
         * The maximum number of concurrent requests that may be in progress.
         * The default value is 2. For the public API, you should only specify
         * a larger value if you have signed up for a plan that allows for
         * more concurrency.
         * @param concurrency the number of concurrent connections. The default is 2.
         * @return this.
         */
        public Builder connectionConcurrency(Integer concurrency) {
            this.concurrency = concurrency;
            return this;
        }

        /**
         * The Apache HTTP components client object to use for communications with the service.
         * If this is not called (or is null), {@link HttpRosetteAPI} will create a client object.
         * @param httpClient the client object.
         * @return this.
         */
        public Builder httpClient(CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Specify an additional header value to include in all requests. This can be helpful for
         * use with corporate proxies, or as directed by Basis Technology customer engineering.
         * @param name the header name.
         * @param value the header value.
         * @return this.
         */
        public Builder additionalHeader(String name, String value) {
            additionalHeaders.add(new BasicHeader(name, value));
            return this;
        }

        /**
         * Only process the response from server if all fields are recognized. If set and a new
         * field is returned in the response, exception will be thrown.
         *
         * @return this.
         */
        public Builder onlyAcceptKnownFields(boolean onlyAcceptKnownFields) {
            this.onlyAcceptKnownFields = onlyAcceptKnownFields;
            return this;
        }

        /**
         * Build the API object.
         * @return the new API object.
         * @throws HttpRosetteAPIException for some error encountered.
         */
        public HttpRosetteAPI build() throws HttpRosetteAPIException {
            return new HttpRosetteAPI(key,
                                      url,
                                      failureRetries,
                                      httpClient,
                                      additionalHeaders,
                                      concurrency,
                                      onlyAcceptKnownFields);
        }
    }
}
