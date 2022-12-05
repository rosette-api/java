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
package com.basistech.rosette.apimodel;

import com.basistech.rosette.dm.AnnotatedText;
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

/**
 * A request to a document-processing endpoint that supplies the input as partially-annotated
 * input in a {@link com.basistech.rosette.dm.AnnotatedText} object.
 */
@Value
public final class AdmRequest<O extends Options> extends Request {
    public static final String ADM_CONTENT_TYPE = "model/vnd.rosette.annotated-data-model";

    /**
     * @return {@link com.basistech.rosette.dm.AnnotatedText}
     */
    private final AnnotatedText text;

    /**
     * @return options
     */
    private final O options;

    /**
     * @return language
     */
    private final LanguageCode language;

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public AdmRequest(String profileId,
                       AnnotatedText text,
                       O options,
                       LanguageCode language) {
        super(profileId);
        this.text = text;
        this.options = options;
        this.language = language;
    }
}
