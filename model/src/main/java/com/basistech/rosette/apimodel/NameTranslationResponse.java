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

package com.basistech.rosette.apimodel;

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.basistech.util.TransliterationScheme;

/**
 * name translation result 
 */
public final class NameTranslationResponse extends Response {
    private final ISO15924 sourceScript;
    private final LanguageCode sourceLanguageOfOrigin;
    private final LanguageCode sourceLanguageOfUse;
    private final LanguageCode targetLanguage;
    private final ISO15924 targetScript;
    private final TransliterationScheme targetScheme;
    private final String translation;
    private final Double confidence;
    
    /**
     * constructor for {@code TranslatedNamesResult}
     * @param sourceScript name's script code
     * @param sourceLanguageOfOrigin name's language of origin
     * @param sourceLanguageOfUse name's language of use
     * @param translation translation
     * @param targetLanguage target language code
     * @param targetScript target script code
     * @param targetScheme target transliteration scheme
     * @param confidence confidence score for result
     */
    public NameTranslationResponse(
            ISO15924 sourceScript,
            LanguageCode sourceLanguageOfOrigin,
            LanguageCode sourceLanguageOfUse,
            String translation,
            LanguageCode targetLanguage,
            ISO15924 targetScript,
            TransliterationScheme targetScheme,
            Double confidence) {
        this.sourceScript = sourceScript;
        this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
        this.sourceLanguageOfUse = sourceLanguageOfUse;
        this.translation = translation;
        this.targetLanguage = targetLanguage;
        this.targetScript = targetScript;
        this.targetScheme = targetScheme;
        this.confidence = confidence;
    }

    /**
     * get the code for the script of the name to translate 
     * @return the source script code
     */
    public ISO15924 getSourceScript() {
        return sourceScript;
    }

    /**
     * get the code for the name's language of origin
     * @return the source language of origin code
     */
    public LanguageCode getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    /**
     * get the code for the name's language of use 
     * @return the source language of use code
     */
    public LanguageCode getSourceLanguageOfUse() {
        return sourceLanguageOfUse;
    }

    /**
     * get the code for the translation language 
     * @return code for the translation language
     */
    public LanguageCode getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * get the code for the translation script 
     * @return code for the translation script
     */
    public ISO15924 getTargetScript() {
        return targetScript;
    }

    /**
     * get the transliteration scheme for the translation 
     * @return code for the transliteration scheme
     */
    public TransliterationScheme getTargetScheme() {
        return targetScheme;
    }

    /**
     * get the translation 
     * @return the translation
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * get the confidence of the translation
     * @return the confidence of the translation
     */
    public Double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        int result;
        result = sourceScript != null ? sourceScript.hashCode() : 0;
        result = 31 * result + (sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.hashCode() : 0);
        result = 31 * result + (sourceLanguageOfUse != null ? sourceLanguageOfUse.hashCode() : 0);
        result = 31 * result + (targetLanguage != null ? targetLanguage.hashCode() : 0);
        result = 31 * result + (targetScript != null ? targetScript.hashCode() : 0);
        result = 31 * result + (targetScheme != null ? targetScheme.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + confidence.hashCode();
        return result;
    }

    /**
     * if the param is a translated name result, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NameTranslationResponse)) {
            return false;
        }

        NameTranslationResponse that = (NameTranslationResponse) o;
        return sourceScript != null ? sourceScript.equals(that.getSourceScript()) : that.sourceScript == null
                && sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.equals(that.getSourceLanguageOfOrigin()) : that.sourceLanguageOfOrigin == null
                && sourceLanguageOfUse != null ? sourceLanguageOfUse.equals(that.getSourceLanguageOfUse()) : that.sourceLanguageOfUse == null
                && targetLanguage != null ? targetLanguage.equals(that.getTargetLanguage()) : that.targetLanguage == null
                && targetScript != null ? targetScript.equals(that.getTargetScript()) : that.targetScript == null
                && targetScheme != null ? targetScheme.equals(that.getTargetScheme()) : that.targetScheme == null
                && confidence.equals(that.getConfidence());
    }
}
