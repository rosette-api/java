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

import com.basistech.rosette.apimodel.Options;
import com.basistech.rosette.dm.AnnotatedText;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.*;

//CHECKSTYLE:OFF
@JsonTypeName("AdmRequest")
public abstract class AdmRequestMixin extends BaseMixin {

    @JsonCreator
    protected AdmRequestMixin(
        @JsonProperty("text") AnnotatedText text,
        @JsonProperty("options") Options options,
        @JsonProperty("genre") String genre,
        @JsonProperty("language") LanguageCode language
            ) {
        //
    }
}
