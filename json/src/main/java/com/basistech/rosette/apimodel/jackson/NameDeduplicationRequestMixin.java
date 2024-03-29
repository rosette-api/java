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

import com.basistech.rosette.apimodel.Name;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

//CHECKSTYLE:OFF
@JsonTypeName("NameDeduplicationRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NameDeduplicationRequestMixin {

    // unable to use builder due to https://github.com/FasterXML/jackson-databind/issues/921
    @JsonCreator
    protected NameDeduplicationRequestMixin(
            @JsonProperty("profileId") String profileId,
            @JsonProperty("names") List<Name> names,
            @JsonProperty("threshold") Double threshold
    ) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract static class NameDeduplicationRequestBuilderMixin {
    }
}
