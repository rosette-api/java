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
import com.basistech.util.TransliterationScheme;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

//CHECKSTYLE:OFF
@JsonTypeName("NameTranslationRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NameTranslationRequestMixin {

    // unable to use builder due to https://github.com/FasterXML/jackson-databind/issues/921
    @JsonCreator
    protected NameTranslationRequestMixin(
            @JsonProperty("profileId") String profileId,
            @JsonProperty("name") String name,
            @JsonProperty("entityType") String entityType,
            @JsonProperty("sourceScript") ISO15924 sourceScript,
            @JsonProperty("sourceLanguageOfOrigin") LanguageCode sourceLanguageOfOrigin,
            @JsonProperty("sourceLanguageOfUse") LanguageCode sourceLanguageOfUse,
            @JsonProperty("targetLanguage") LanguageCode targetLanguage,
            @JsonProperty("targetScript") ISO15924 targetScript,
            @JsonProperty("targetScheme") TransliterationScheme targetScheme
    ) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract class NameTranslationRequestBuilderMixin {
    }
}
