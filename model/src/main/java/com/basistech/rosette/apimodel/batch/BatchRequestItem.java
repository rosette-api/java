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
package com.basistech.rosette.apimodel.batch;

import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.Request;

/**
 * One item in a batch request. Each item consists of an input
 * to process and an endpoint to apply to the item.
 * The inputs are specified by {@link com.basistech.rosette.apimodel.Request} objects.
 * The special content type {@code application/vnd.basistech-multiple-inputs}
 * used in {@link Request#contentType} identifies
 * a json file consisting of an array of strings; each string being treated as an
 * input. The string length in this case is limited to 200 characters.
 */
public class BatchRequestItem {
    private final String endpoint;
    //TODO: make this, well, 'Object', or an empty abstract class, and set up polymorphism.
    private final Request documentRequest;
    private final NameTranslationRequest nameTranslationRequest;
    private final NameSimilarityRequest nameSimilarityRequest;
    private final String id;

    /**
     * Create an item.
     * @param endpoint the endpoint
     * @param documentRequest the request- for a document-processing endpoint.
     * @param nameTranslationRequest the request for name translation.
     * @param nameSimilarityRequest the request for a name similarity request.
     * @param id
     */
    public BatchRequestItem(String endpoint, Request documentRequest, NameTranslationRequest nameTranslationRequest, NameSimilarityRequest nameSimilarityRequest, String id) {
        this.endpoint = endpoint;
        this.documentRequest = documentRequest;
        this.nameTranslationRequest = nameTranslationRequest;
        this.nameSimilarityRequest = nameSimilarityRequest;
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Request getDocumentRequest() {
        return documentRequest;
    }

    public NameTranslationRequest getNameTranslationRequest() {
        return nameTranslationRequest;
    }

    public NameSimilarityRequest getNameSimilarityRequest() {
        return nameSimilarityRequest;
    }

    public String getId() {
        return id;
    }
}
