/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * name translation result 
 */
public final class NameTranslationResult {

    private final String sourceScript;
    private final String sourceLanguageOfOrigin;
    private final String sourceLanguageOfUse;
    private final String targetLanguage;
    private final String targetScript;
    private final String targetScheme;
    private final String translation;
    private final double confidence;
    
    /**
     * constructor for {@code TranslatedNamesResult}
     * @param sourceScript name's script code
     * @param sourceLanguageOfOrigin name's language of origin
     * @param sourceLanguageOfUse name's language of use
     * @param translation translation
     * @param targetLanguage target language code
     * @param targetScript target script code
     * @param targetScheme target transliteration scheme*
     */
    public NameTranslationResult(
            String sourceScript,
            String sourceLanguageOfOrigin,
            String sourceLanguageOfUse,
            String translation,
            String targetLanguage,
            String targetScript,
            String targetScheme,
            double confidence) {
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
    public String getSourceScript() {
        return sourceScript;
    }

    /**
     * get the code for the name's language of origin
     * @return the source language of origin code
     */
    public String getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    /**
     * get the code for the name's language of use 
     * @return the source language of use code
     */
    public String getSourceLanguageOfUse() {
        return sourceLanguageOfUse;
    }

    /**
     * get the code for the translation language 
     * @return code for the translation language
     */
    public String getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * get the code for the translation script 
     * @return code for the translation script
     */
    public String getTargetScript() {
        return targetScript;
    }

    /**
     * get the transliteration scheme for the translation 
     * @return code for the transliteration scheme
     */
    public String getTargetScheme() {
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
    public double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = sourceScript != null ? sourceScript.hashCode() : 0;
        result = 31 * result + (sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.hashCode() : 0);
        result = 31 * result + (sourceLanguageOfUse != null ? sourceLanguageOfUse.hashCode() : 0);
        result = 31 * result + (targetLanguage != null ? targetLanguage.hashCode() : 0);
        result = 31 * result + (targetScript != null ? targetScript.hashCode() : 0);
        result = 31 * result + (targetScheme != null ? targetScheme.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * if the param is a translated name result, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NameTranslationResult)) {
            return false;
        }

        NameTranslationResult that = (NameTranslationResult) o;
        return sourceScript != null ? sourceScript.equals(that.getSourceScript()) : that.sourceScript == null
                && sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.equals(that.getSourceLanguageOfOrigin()) : that.sourceLanguageOfOrigin == null
                && sourceLanguageOfUse != null ? sourceLanguageOfUse.equals(that.getSourceLanguageOfUse()) : that.sourceLanguageOfUse == null
                && targetLanguage != null ? targetLanguage.equals(that.getTargetLanguage()) : that.targetLanguage == null
                && targetScript != null ? targetScript.equals(that.getTargetScript()) : that.targetScript == null
                && targetScheme != null ? targetScheme.equals(that.getTargetScheme()) : that.targetScheme == null
                && confidence == that.getConfidence();
    }
}
