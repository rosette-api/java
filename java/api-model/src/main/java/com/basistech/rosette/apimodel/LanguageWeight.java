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
 * language weight used to resolve ambiguous results
 */
public final class LanguageWeight {

    private final LanguageCode language;
    private final ISO15924 script;
    private final Integer weight;

    /**
     * constructor for {@code LanguageWeight} 
     * @param language language
     * @param script script
     * @param weight weight used to resolve ambiguous results
     */
    public LanguageWeight(
            LanguageCode language,
            ISO15924 script,
            Integer weight
    ) {
        this.language = language;
        this.script = script;
        this.weight = weight;
    }

    /**
     * constructor for {@code LanguageWeight}
     * @param language language
     * @param weight weight used to resolve ambiguous results
     */
    public LanguageWeight(
            LanguageCode language,
            Integer weight
    ) {
        this.language = language;
        this.script = null;
        this.weight = weight;
    }

    /**
     * get the language 
     * @return the language
     */
    public LanguageCode getLanguage() {
        return language;
    }

    /**
     * get the script 
     * @return the script
     */
    public ISO15924 getScript() {
        return script;
    }

    /**
     * get the weight used to resolve ambiguous results
     * @return the weight
     */
    public Integer getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code LanguageWeight}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LanguageWeight)) {
            return false;
        }

        LanguageWeight that = (LanguageWeight) o;
        return language != null ? language.equals(that.getLanguage()) : that.language == null
                && weight != null ? weight.equals(that.getWeight()) : that.weight == null
                && script != null ? script.equals(that.getScript()) : that.script == null;
    }
}
