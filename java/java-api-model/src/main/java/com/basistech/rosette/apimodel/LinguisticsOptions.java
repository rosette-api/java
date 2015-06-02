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
 * Linguistics analysis options 
 */
public final class LinguisticsOptions {

    private final Boolean disambiguate;
    private final Boolean query;
    private final Boolean tokenizeForScript;
    private final Integer minNonPrimaryScriptRegionLength;
    private final Boolean includeHebrewRoots;
    private final Boolean nfkcNormalize;
    private final Boolean fstTokenize;
    private final String defaultTokenizationLanguage;

    /**
     * constructor for {@code LinguisticsOptions} 
     * @param disambiguate whether the linguistics analysis should disambiguate results
     * @param query request query processing
     * @param tokenizeForScript whether to use different tokenizers for different scripts
     * @param minNonPrimaryScriptRegionLength minimum non-primary scripting region length
     * @param includeHebrewRoots whether to include Hebrew roots
     * @param nfkcNormalize whether to enable Unicode normalization before tokenization
     * @param fstTokenize whether to enable tokenization for supported languages
     * @param defaultTokenizationLanguage default tokenization language
     */
    public LinguisticsOptions(
            Boolean disambiguate,
            Boolean query,
            Boolean tokenizeForScript,
            Integer minNonPrimaryScriptRegionLength,
            Boolean includeHebrewRoots,
            Boolean nfkcNormalize,
            Boolean fstTokenize,
            String defaultTokenizationLanguage
    ) {
        this.disambiguate = disambiguate;
        this.query = query;
        this.tokenizeForScript = tokenizeForScript;
        this.minNonPrimaryScriptRegionLength = minNonPrimaryScriptRegionLength;
        this.includeHebrewRoots = includeHebrewRoots;
        this.nfkcNormalize = nfkcNormalize;
        this.fstTokenize = fstTokenize;
        this.defaultTokenizationLanguage = defaultTokenizationLanguage;
    }

    /**
     * get whether the linguistics analysis should disambiguate results
     * @return whether the linguistics analysis should disambiguate results
     */
    public Boolean getDisambiguate() {
        return disambiguate;
    }

    /**
     * Request query processing. Linguistics analysis may change its behavior
     * to reflect the fact that query input is often not in full sentences;
     * typically, this disables disambiguation 
     * @return request query processing
     */
    public Boolean getQuery() {
        return query;
    }

    /**
     * Get whether to use different tokenizers for different scripts. 
     * If false, uses the tokenizer for the {@code defaultTokenizationLanguage}. 
     * Applies only to statistical segmentation languages 
     * @return whether to use different tokenizers for different scripts
     */
    public Boolean getTokenizeForScript() {
        return tokenizeForScript;
    }

    /**
     * get the minimum non-primary scripting region length
     * @return minimum non-primary scripting region length
     */
    public Integer getMinNonPrimaryScriptRegionLength() {
        return minNonPrimaryScriptRegionLength;
    }

    /**
     * get whether to include Hebrew roots
     * @return whether to include Hebrew roots
     */
    public Boolean getIncludeHebrewRoots() {
        return includeHebrewRoots;
    }

    /**
     * get whether to enable Unicode normalization before tokenization
     * @return whether to enable Unicode normalization before tokenization
     */
    public Boolean getNfkcNormalize() {
        return nfkcNormalize;
    }

    /**
     * get whether to enable tokenization for supported languages
     * @return whether to enable tokenization for supported languages
     */
    public Boolean getFstTokenize() {
        return fstTokenize;
    }

    /**
     * get the default tokenization language 
     * @return the default tokenization language
     */
    public String getDefaultTokenizationLanguage() {
        return defaultTokenizationLanguage;
    }

    @Override
    public int hashCode() {
        int result = disambiguate != null ? disambiguate.hashCode() : 0;
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + (tokenizeForScript != null ? tokenizeForScript.hashCode() : 0);
        result = 31 * result + (minNonPrimaryScriptRegionLength != null ? minNonPrimaryScriptRegionLength.hashCode() : 0);
        result = 31 * result + (includeHebrewRoots != null ? includeHebrewRoots.hashCode() : 0);
        result = 31 * result + (nfkcNormalize != null ? nfkcNormalize.hashCode() : 0);
        result = 31 * result + (fstTokenize != null ? fstTokenize.hashCode() : 0);
        result = 31 * result + (defaultTokenizationLanguage != null ? defaultTokenizationLanguage.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code LinguisticsOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LinguisticsOptions) {
            LinguisticsOptions that = (LinguisticsOptions) o;
            return disambiguate != null ? disambiguate.equals(that.getDisambiguate()) : that.disambiguate == null
                    && query != null ? query.equals(that.getQuery()) : that.query == null
                    && tokenizeForScript != null ? tokenizeForScript.equals(that.getTokenizeForScript()) : that.tokenizeForScript == null
                    && minNonPrimaryScriptRegionLength != null ? minNonPrimaryScriptRegionLength.equals(that.getMinNonPrimaryScriptRegionLength()) : that.minNonPrimaryScriptRegionLength == null
                    && includeHebrewRoots != null ? includeHebrewRoots.equals(that.getIncludeHebrewRoots()) : that.includeHebrewRoots == null
                    && nfkcNormalize != null ? nfkcNormalize.equals(that.getNfkcNormalize()) : that.nfkcNormalize == null
                    && fstTokenize != null ? fstTokenize.equals(that.getNfkcNormalize()) : that.fstTokenize == null
                    && defaultTokenizationLanguage != null ? defaultTokenizationLanguage.equals(that.getDefaultTokenizationLanguage()) : that.defaultTokenizationLanguage == null;
        } else {
            return false;
        }
    }
}
