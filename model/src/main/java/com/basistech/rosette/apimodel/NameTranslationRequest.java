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

import javax.validation.constraints.NotNull;

/**
 * Class that represents the data from a name translation request
 */
public final class NameTranslationRequest {
    @NotNull
    private String name;
    private String entityType;
    private ISO15924 sourceScript;
    private LanguageCode sourceLanguageOfOrigin;
    private LanguageCode sourceLanguageOfUse;
    @NotNull
    private LanguageCode targetLanguage;
    @NotNull
    private ISO15924 targetScript;
    @NotNull
    private TransliterationScheme targetScheme;

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
    protected NameTranslationRequest(String name,
                                  String entityType,
                                  ISO15924 sourceScript,
                                  LanguageCode sourceLanguageOfOrigin,
                                  LanguageCode sourceLanguageOfUse,
                                  LanguageCode targetLanguage,
                                  ISO15924 targetScript,
                                  TransliterationScheme targetScheme) {
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
    public ISO15924 getSourceScript() {
        return sourceScript;
    }

    /**
     * get the code for the name's language of origin 
     * @return code for the name's language of origin
     */
    public LanguageCode getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    /**
     * get the code for the name's language of use 
     * @return code for the name's language of use
     */
    public LanguageCode getSourceLanguageOfUse() {
        return sourceLanguageOfUse;
    }

    /**
     * get code for the translation language
     * @return code for the translation language
     */
    public LanguageCode getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * get the code for the target script 
     * @return code for the target script
     */
    public ISO15924 getTargetScript() {
        return targetScript;
    }

    /**
     * get the transliteration scheme for the translation
     * @return the transliteration scheme for the translation
     */
    public TransliterationScheme getTargetScheme() {
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
        if (!(o instanceof NameTranslationRequest)) {
            return false;
        }

        NameTranslationRequest that = (NameTranslationRequest) o;
        return name != null ? name.equals(that.getName()) : that.name == null
                && entityType != null ? entityType.equals(that.getEntityType()) : that.entityType == null
                && sourceScript != null ? sourceScript.equals(that.getSourceScript()) : that.sourceScript == null
                && sourceLanguageOfOrigin != null ? sourceLanguageOfOrigin.equals(that.getSourceLanguageOfOrigin()) : that.sourceLanguageOfOrigin == null
                && sourceLanguageOfUse != null ? sourceLanguageOfUse.equals(that.getSourceLanguageOfUse()) : that.sourceLanguageOfUse == null
                && targetLanguage != null ? targetLanguage.equals(that.getTargetLanguage()) : that.targetLanguage == null
                && targetScript != null ? targetScript.equals(that.getTargetScript()) : that.targetScript == null
                && targetScheme != null ? targetScheme.equals(that.getTargetScheme()) : that.targetScheme == null;
    }

    public static class Builder {
        private String name;
        private String entityType;
        private ISO15924 sourceScript;
        private LanguageCode sourceLanguageOfOrigin;
        private LanguageCode sourceLanguageOfUse;
        private LanguageCode targetLanguage;
        private ISO15924 targetScript;
        private TransliterationScheme targetScheme;

        public Builder(String name, LanguageCode targetLanguage, ISO15924 targetScript, TransliterationScheme targetScheme) {
            this.name = name;
            this.targetLanguage = targetLanguage;
            this.targetScript = targetScript;
            this.targetScheme = targetScheme;
        }

        public Builder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public String entityType() {
            return entityType;
        }

        public Builder sourceScript(ISO15924 sourceScript) {
            this.sourceScript = sourceScript;
            return this;
        }

        public ISO15924 sourceScript() {
            return sourceScript;
        }

        public Builder sourceLanguageOfUse(LanguageCode sourceLanguageOfUse) {
            this.sourceLanguageOfUse = sourceLanguageOfUse;
            return this;
        }

        public LanguageCode sourceLanguageOfUse() {
            return sourceLanguageOfUse;
        }

        public Builder sourceLanguageOfOrigin(LanguageCode sourceLanguageOfOrigin) {
            this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
            return this;
        }

        public LanguageCode sourceLanguageOfOrigin() {
            return sourceLanguageOfOrigin;
        }

        public NameTranslationRequest build() {
            return new NameTranslationRequest(name, entityType, sourceScript, sourceLanguageOfOrigin,
                    sourceLanguageOfUse, targetLanguage, targetScript, targetScheme);
        }

    }
}
