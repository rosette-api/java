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

import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.InputStream;

//CHECKSTYLE:OFF
public abstract class RequestMixin extends BaseMixin {

    public static class Views {
        public static class Content {
            //
        }
    }

    @JsonCreator
    protected RequestMixin(
            @JsonProperty("language") LanguageCode language,
            @JsonProperty("genre") String genre,
            @JsonProperty("content") Object content,
            @JsonProperty("contentUri") String contentUri,
            @JsonProperty("contentType") String contentType
            ) {
        //
    }

    @JsonIgnore
    public abstract Object getRawContent();

    @JsonIgnore
    public abstract InputStream getContentBytes();

    // the content type goes onto the wire as part of the multipart request.
    @JsonIgnore
    public abstract String getContentType();

    @JsonView(Views.Content.class)
    public abstract String getContent();
}
