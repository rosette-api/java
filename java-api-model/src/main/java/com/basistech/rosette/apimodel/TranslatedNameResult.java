/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * name translation result 
 */
public class TranslatedNameResult {

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
    public TranslatedNameResult(
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
        if (o instanceof TranslatedNameResult) {
            TranslatedNameResult that = (TranslatedNameResult) o;
            return sourceScript != null ? sourceScript.equals(that.getSourceScript()) : sourceScript == that.getSourceScript()
                    && sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.equals(that.getSourceLanguageOfOrigin()) : sourceLanguageOfOrigin == that.getSourceLanguageOfOrigin()
                    && sourceLanguageOfUse != null ? sourceLanguageOfUse.equals(that.getSourceLanguageOfUse()) : sourceLanguageOfUse == that.getSourceLanguageOfUse()
                    && targetLanguage != null ? targetLanguage.equals(that.getTargetLanguage()) : targetLanguage == that.getTargetLanguage()
                    && targetScript != null ? targetScript.equals(that.getTargetScript()) : targetScript == that.getTargetScript()
                    && targetScheme != null ? targetScheme.equals(that.getTargetScheme()) : targetScheme == that.getTargetScheme()
                    && confidence == that.getConfidence();
        } else {
            return false;
        }
    }
}
