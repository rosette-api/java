/*
 * Copyright 2022 Basis Technology Corp.
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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import com.basistech.rosette.apimodel.IAddress;

@JsonTypeName("AddressSimilarityRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressSimilarityRequestMixin {

    @JsonCreator
    protected AddressSimilarityRequestMixin(
            @JsonProperty("profileId") String profileId,
            @JsonProperty("address1") IAddress address1,
            @JsonProperty("address2") IAddress address2,
            @JsonProperty("parameters") Map<String, String> parameters
    ) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract static class AddressSimilarityRequestBuilderMixin {
    }
}
