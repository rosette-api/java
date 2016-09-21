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
package com.basistech.rosette.apimodel;

import com.basistech.rosette.dm.AnnotatedText;
import com.basistech.util.LanguageCode;

/**
 * A request to a document-processing endpoint that supplies the input as partially-annotated
 * input in a {@link AnnotatedText} object.
 */
public class AdmRequest<O extends Options> extends Request {
    private final AnnotatedText text;
    private final O options;
    private final String genre;
    private final LanguageCode language;

    /**
     * Construct the request.
     * @param text the annotated text.
     * @param options the options, or null.
     * @param genre the genre, or null.
     * @param language the language, or null.
     */
    public AdmRequest(AnnotatedText text, O options, String genre, LanguageCode language) {
        this.text = text;
        this.options = options;
        this.genre = genre;
        this.language = language;
    }

    public AnnotatedText getText() {
        return text;
    }

    public O getOptions() {
        return options;
    }

    public String getGenre() {
        return genre;
    }

    public LanguageCode getLanguage() {
        return language;
    }
}
