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

import com.basistech.rosette.apimodel.LanguageWeight;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

@JsonTypeName("LanguageOptions")
public class LanguageOptionsMixin extends OptionsMixin {
    @JsonCreator
    protected LanguageOptionsMixin(
            @JsonProperty("multilingual") Boolean multilingual,
            @JsonProperty("minValidChars") Integer minValidChars,
            @JsonProperty("profileDepth") Integer profileDepth,
            @JsonProperty("ambiguityThreshold") Double ambiguityThreshold,
            @JsonProperty("invalidityThreshold") Double invalidityThreshold,
            @JsonProperty("languageHint") LanguageCode languageHint,
            @JsonProperty("languageHintWeight") Double languageHintWeight,
            @JsonProperty("encodingHint") String encodingHint,
            @JsonProperty("encodingHintWeight") Double encodingHintWeight,
            @JsonProperty("languageWeightAdjustments") Set<LanguageWeight> languageWeightAdjustments
    ) {
        //
    }
}
