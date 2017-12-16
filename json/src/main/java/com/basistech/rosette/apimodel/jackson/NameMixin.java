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

package com.basistech.rosette.apimodel.jackson;

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

//CHECKSTYLE:OFF
@JsonTypeName("Name")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NameMixin {

    // Need to stick to a non-builder mixin to support single-string constructor
    // see the model code for details
    @JsonCreator
    protected NameMixin (
            @JsonProperty("name") String name,
            @JsonProperty("entityType") String entityType,
            @JsonProperty("script") ISO15924 script,
            @JsonProperty("language") LanguageCode language
    ) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract class NameBuilderMixin {
    }
}
