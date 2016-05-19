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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.RelationshipsOptions;
import com.basistech.rosette.apimodel.SentimentOptions;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CategoriesOptions.class, name = "CategoriesOptions"),
        @JsonSubTypes.Type(value = EntitiesOptions.class, name = "EntitiesOptions"),
        @JsonSubTypes.Type(value = LanguageOptions.class, name = "LanguageOptions"),
        @JsonSubTypes.Type(value = MorphologyOptions.class, name = "MorphologyOptions"),
        @JsonSubTypes.Type(value = RelationshipsOptions.class, name = "RelationshipsOptions"),
        @JsonSubTypes.Type(value = SentimentOptions.class, name = "SentimentOptions")
    })
public abstract class OptionsMixin extends BaseMixin {
    //
}
