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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RelationshipsMixin extends BaseMixin {
    @JsonCreator
    public RelationshipsMixin(
            @JsonProperty("predicate") String predicate,
            @JsonProperty("predicateId") String predicateId,
            @JsonProperty("arg1") String arg1,
            @JsonProperty("arg1Id") String arg1Id,
            @JsonProperty("arg2") String arg2,
            @JsonProperty("arg2Id") String arg2Id,
            @JsonProperty("arg3") String arg3,
            @JsonProperty("arg3Id") String arg3Id,
            @JsonProperty("adjuncts") List<String> adjuncts,
            @JsonProperty("context") String context,
            @JsonProperty("source") String source,
            @JsonProperty("confidence") Double confidence
    ) {
        //
    }
}
