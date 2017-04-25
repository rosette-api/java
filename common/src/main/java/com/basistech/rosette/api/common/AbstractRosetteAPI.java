/*
* Copyright 2017 Basis Technology Corp.
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

package com.basistech.rosette.api.common;

import java.util.concurrent.Future;

import com.basistech.rosette.apimodel.CommonRosetteAPIException;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.dm.AnnotatedText;

/**
 * This class defines the common API to Rosette, whether over HTTP or other integration mechanisms.
 */
public abstract class AbstractRosetteAPI implements AutoCloseable {

    public static final String LANGUAGE_SERVICE_PATH = "/language";
    public static final String MORPHOLOGY_SERVICE_PATH = "/morphology";
    public static final String ENTITIES_SERVICE_PATH = "/entities";
    public static final String CATEGORIES_SERVICE_PATH = "/categories";
    public static final String RELATIONSHIPS_SERVICE_PATH = "/relationships";
    public static final String SENTIMENT_SERVICE_PATH = "/sentiment";
    public static final String NAME_DEDUPLICATION_SERVICE_PATH = "/name-deduplication";
    public static final String NAME_TRANSLATION_SERVICE_PATH = "/name-translation";
    public static final String NAME_SIMILARITY_SERVICE_PATH = "/name-similarity";
    public static final String TOKENS_SERVICE_PATH = "/tokens";
    public static final String SENTENCES_SERVICE_PATH = "/sentences";
    public static final String TEXT_EMBEDDING_SERVICE_PATH = "/text-embedding";
    public static final String SYNTAX_DEPENDENCIES_SERVICE_PATH = "/syntax/dependencies";
    public static final String TRANSLITERATION_SERVICE_PATH = "/transliteration";
    public static final String INFO_SERVICE_PATH = "/info";
    public static final String PING_SERVICE_PATH = "/ping";

    /**
     * Perform a request to an endpoint of the Rosette API.
     * @param endpoint which endpoint.
     * @param request the data for the request.
     * @param <RequestType> The class of the request object for this endpoint.
     * @param <ResponseType> the class of the response object for this endpoint.
     * @param responseClass the Java {@link Class} object for the response object.
     * @return the response.
     * @throws CommonRosetteAPIException for an error.
     */
    public abstract <RequestType extends Request, ResponseType extends Response> ResponseType perform(String endpoint, RequestType request, Class<ResponseType> responseClass) throws CommonRosetteAPIException;

    /**
     * Perform a request to an endpoint of the Rosette API.
     * @param endpoint which endpoint.
     * @param request the data for the request.
     * @param <RequestType> The class of the request object for this endpoint.
     * @return the response, {@link com.basistech.rosette.dm.AnnotatedText}.
     * @throws CommonRosetteAPIException for an error.
     */
    public abstract <RequestType extends Request> AnnotatedText perform(String endpoint, RequestType request) throws CommonRosetteAPIException;

    /**
     * Start an asynchronous request to an endpoint of the Rosette API.
     * @param endpoint which endpoint.
     * @param request the data for the request.
     * @param <RequestType> The class of the request object for this endpoint.
     * @param <ResponseType> the class of the response object for this endpoint.
     * @param responseClass the Java {@link Class} object for the response object.
     * @return a {@link Future} for the response.
     * @throws CommonRosetteAPIException for an error.
     */
    public abstract <RequestType extends Request, ResponseType extends Response> Future<ResponseType> performAsync(String endpoint, RequestType request, Class<ResponseType> responseClass) throws CommonRosetteAPIException;
}
