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


package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MorphologyResponseMixin extends BaseMixin {
    @JsonCreator
    public MorphologyResponseMixin(@JsonProperty("tokens") List<String> tokens,
                                   @JsonProperty("posTags") List<String> posTags,
                                   @JsonProperty("lemmas") List<String> lemmas,
                                   @JsonProperty("compoundComponents") List<List<String>> compoundComponents,
                                   @JsonProperty("hanReadings") List<List<String>> hanReadings) {
        //
    }
}
