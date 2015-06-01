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
 * language weight used to resolve ambiguous results
 */
public class LanguageWeight {
    private final String language;
    private final String script;
    private final Integer weight;

    /**
     * constructor for {@code LanguageWeight} 
     * @param language language
     * @param script script
     * @param weight weight used to resolve ambiguous results
     */
    public LanguageWeight(
            String language,
            String script,
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
            String language,
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
    public String getLanguage() {
        return language;
    }

    /**
     * get the script 
     * @return the script
     */
    public String getScript() {
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
        if (o instanceof LanguageWeight) {
            LanguageWeight that = (LanguageWeight) o;
            return language != null ? language.equals(that.getLanguage()) : language == that.getLanguage()
                    && weight != null ? weight.equals(that.getWeight()) : weight == that.getWeight()
                    && script != null ? script.equals(that.getScript()) : script == that.getScript();
        } else {
            return false;
        }
    }
}
