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
 * Class that represents the data from a RaaS client name translation request
 */
// todo: should this extend request?
public class NameTranslationRequest {

    private final String name;
    private final String entityType;
    private final String sourceScript;
    private final String sourceLanguageOfOrigin;
    private final String sourceLanguageOfUse;
    private final String targetLanguage;
    private final String targetScript;
    private final String targetScheme;
    
    /**
     * constructor for {@code NameTranslationRequest}
     * @param name name to be translated
     * @param entityType name's entity type
     * @param sourceScript name's script code
     * @param sourceLanguageOfOrigin name's language of origin
     * @param sourceLanguageOfUse name's language of use
     * @param targetLanguage target language code
     * @param targetScript target script code
     * @param targetScheme target transliteration scheme
     */
    public NameTranslationRequest(String name,
                                  String entityType,
                                  String sourceScript,
                                  String sourceLanguageOfOrigin,
                                  String sourceLanguageOfUse,
                                  String targetLanguage,
                                  String targetScript,
                                  String targetScheme) {
        this.name = name;
        this.entityType = entityType;
        this.sourceScript = sourceScript;
        this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
        this.sourceLanguageOfUse = sourceLanguageOfUse;
        this.targetLanguage = targetLanguage;
        this.targetScript = targetScript;
        this.targetScheme = targetScheme;
    }

    /**
     * get the name to be translated 
     * @return the name to be translated 
     */
    public String getName() {
        return name;
    }

    /**
     * get the entity type of the name
     * @return the entity type of the name
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * get the code for the name's script
     * @return code for the name's script
     */
    public String getSourceScript() {
        return sourceScript;
    }

    /**
     * get the code for the name's language of origin 
     * @return code for the name's language of origin
     */
    public String getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    /**
     * get the code for the name's language of use 
     * @return code for the name's language of use
     */
    public String getSourceLanguageOfUse() { return sourceLanguageOfUse; }

    /**
     * get code for the translation language
     * @return code for the translation language
     */
    public String getTargetLanguage() { return targetLanguage; }

    /**
     * get the code for the target script 
     * @return code for the target script
     */
    public String getTargetScript() {
        return targetScript;
    }

    /**
     * get the transliteration scheme for the translation
     * @return the transliteration scheme for the translation
     */
    public String getTargetScheme() {
        return targetScheme;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (entityType != null ? entityType.hashCode() : 0);
        result = 31 * result + (sourceScript != null ? sourceScript.hashCode() : 0);
        result = 31 * result + (sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.hashCode() : 0);
        result = 31 * result + (sourceLanguageOfUse != null ? sourceLanguageOfUse.hashCode() : 0);
        result = 31 * result + (targetLanguage != null ? targetLanguage.hashCode() : 0);
        result = 31 * result + (targetScript != null ? targetScript.hashCode() : 0);
        result = 31 * result + (targetScheme != null ? targetScheme.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code NameTranslationRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof NameTranslationRequest) {
            NameTranslationRequest that = (NameTranslationRequest) o;
            return name != null ? name.equals(that.getName()) : that.name == null
                    && entityType != null ? entityType.equals(that.getEntityType()) : that.entityType == null
                    && sourceScript != null ? sourceScript.equals(that.getSourceScript()) : that.sourceScript == null
                    && sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.equals(that.getSourceLanguageOfOrigin()) : that.sourceLanguageOfOrigin == null
                    && sourceLanguageOfUse != null ? sourceLanguageOfUse.equals(that.getSourceLanguageOfUse()) : that.sourceLanguageOfUse == null
                    && targetLanguage != null ? targetLanguage.equals(that.getTargetLanguage()) : that.targetLanguage == null
                    && targetScript != null ? targetScript.equals(that.getTargetScript()) : that.targetScript == null
                    && targetScheme != null ? targetScheme.equals(that.getTargetScheme()) : that.targetScheme == null;
        } else {
            return false;
        }
    }
}
