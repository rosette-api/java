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

package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameTranslationRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
              include = JsonTypeInfo.As.PROPERTY,
              property = "type",
              defaultImpl = DocumentRequest.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DocumentRequest.class, name = "DocumentRequest"),
        @JsonSubTypes.Type(value = NameSimilarityRequest.class, name = "NameSimilarityRequest"),
        @JsonSubTypes.Type(value = NameTranslationRequest.class, name = "NameTranslationRequest")})
public abstract class RequestMixin {
    //
}
