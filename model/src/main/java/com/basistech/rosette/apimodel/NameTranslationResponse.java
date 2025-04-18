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

import java.util.List;
import java.util.Map;

import com.basistech.rosette.annotations.JacksonMixin;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.TransliterationScheme;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * name translation result 
 */
@Getter
@EqualsAndHashCode
@Builder
@JacksonMixin
public class NameTranslationResponse extends Response {

    /**
     * @return the code for the script of the name to translate
     */
    private final ISO15924 sourceScript;

    /**
     * @return the source name's language of origin code
     */
    private final LanguageCode sourceLanguageOfOrigin;

    /**
     * @return the source name's language of use code
     */
    private final LanguageCode sourceLanguageOfUse;

    /**
     * @return the target name's language code
     */
    private final LanguageCode targetLanguage;

    /**
     * @return the target name's script code
     */
    private final ISO15924 targetScript;

    /**
     * @return the transliteration scheme used
     */
    private final TransliterationScheme targetScheme;

    /**
     * @return the translation
     */
    private final String translation;

    /**
     * @return the translation confidence (0.0-1.0)
     */
    private final Double confidence;

    /**
     * @return the translation
     */
    private final List<NameTranslation> translations;

    public static class NameTranslationResponseBuilder {
        protected Map<String, Object> extendedInformation;

        public NameTranslationResponseBuilder extendedInformation(Map<String, Object> extendedInformation) {
            this.extendedInformation = extendedInformation;
            return this;
        }


        public NameTranslationResponse build() {
            NameTranslationResponse response = new NameTranslationResponse(
                    this.sourceScript,
                    this.sourceLanguageOfOrigin,
                    this.sourceLanguageOfUse,
                    this.targetLanguage,
                    this.targetScript,
                    this.targetScheme,
                    this.translation,
                    this.confidence,
                    this.translations);
            response.addExtendedInformation(extendedInformation);
            return response;
        }
    }
}
