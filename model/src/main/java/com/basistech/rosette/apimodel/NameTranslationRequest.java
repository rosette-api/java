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

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.TransliterationScheme;
import lombok.Builder;
import lombok.Value;

import jakarta.validation.constraints.NotNull;

/**
 * Request for name translation.
 */
@Value
public final class NameTranslationRequest extends Request {

    /**
     * @return the name to be translated
     */
    @NotNull
    private final String name;

    /**
     * @return the entity type of the name
     */
    private final String entityType;

    /**
     * @return code for the name's script
     */
    private final ISO15924 sourceScript;

    /**
     * @return code for the name's language of origin
     */
    private final LanguageCode sourceLanguageOfOrigin;

    /**
     * @return code for the name's language of use
     */
    private final LanguageCode sourceLanguageOfUse;

    /**
     * @return code for the language to translate to
     */
    @NotNull
    private final LanguageCode targetLanguage;

    /**
     * @return code for the target script
     */
    private final ISO15924 targetScript;

    /**
     * @return the transliteration scheme for the translation
     */
    private final TransliterationScheme targetScheme;

    /**
     * @return the maximum number of translation results to return
     */
    private final Integer maximumResults;

    public NameTranslationRequest(String profileId,
                                   String name,
                                   String entityType,
                                   ISO15924 sourceScript,
                                   LanguageCode sourceLanguageOfOrigin,
                                   LanguageCode sourceLanguageOfUse,
                                   LanguageCode targetLanguage,
                                   ISO15924 targetScript,
                                   TransliterationScheme targetScheme) {
        this(profileId, name, entityType, sourceScript, sourceLanguageOfOrigin, sourceLanguageOfUse, targetLanguage, targetScript, targetScheme, null);
    }

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public NameTranslationRequest(String profileId,
                                  String name,
                                  String entityType,
                                  ISO15924 sourceScript,
                                  LanguageCode sourceLanguageOfOrigin,
                                  LanguageCode sourceLanguageOfUse,
                                  LanguageCode targetLanguage,
                                  ISO15924 targetScript,
                                  TransliterationScheme targetScheme,
                                  Integer maximumResults) {
        super(profileId);
        this.name = name;
        this.entityType = entityType;
        this.sourceScript = sourceScript;
        this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
        this.sourceLanguageOfUse = sourceLanguageOfUse;
        this.targetLanguage = targetLanguage;
        this.targetScript = targetScript;
        this.targetScheme = targetScheme;
        this.maximumResults = maximumResults;
    }
}
