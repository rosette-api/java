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
package com.basistech.rosette.apimodel.batch;

import com.basistech.rosette.apimodel.Request;
import lombok.Builder;
import lombok.Value;

/**
 * One item in a batch request. Each item consists of an input
 * to process and an endpoint to apply to the item.
 * The inputs are specified by {@link Request} objects.
 * If the request type is {@link com.basistech.rosette.apimodel.DocumentRequest}, the special content type
 * {@code application/vnd.basistech-multiple-inputs}
 * used in {@link com.basistech.rosette.apimodel.DocumentRequest#contentType} identifies
 * a json file consisting of an array of strings; each string being treated as an
 * input. The string length in this case is limited to 200 characters.
 */
@Value
@Builder
public class BatchRequestItem {
    private final String endpoint;
    private final Request request;
    private final String id;
}
