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
 * Linguistics analysis options 
 */
public final class MorphologyOptions {

    private Boolean disambiguate;
    private Boolean query;
    private Boolean tokenizeForScript;
    private Integer minNonPrimaryScriptRegionLength;
    private Boolean includeHebrewRoots;
    private Boolean nfkcNormalize;
    private Boolean fstTokenize;
    private String defaultTokenizationLanguage;

    /**
     * constructor for {@code MorphologyOptions}
     * @param disambiguate whether the linguistics analysis should disambiguate results
     * @param query request query processing
     * @param tokenizeForScript whether to use different tokenizers for different scripts
     * @param minNonPrimaryScriptRegionLength minimum non-primary scripting region length
     * @param includeHebrewRoots whether to include Hebrew roots
     * @param nfkcNormalize whether to enable Unicode normalization before tokenization
     * @param fstTokenize whether to enable tokenization for supported languages
     * @param defaultTokenizationLanguage default tokenization language
     */
    public MorphologyOptions(
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

    /**
     * set whether the linguistics analysis should disambiguate results
     * @param disambiguate whether the linguistics analysis should disambiguate results
     */
    public void setDisambiguate(Boolean disambiguate) {
        this.disambiguate = disambiguate;
    }

    /**
     * Request query processing. Linguistics analysis may change its behavior
     * to reflect the fact that query input is often not in full sentences;
     * typically, this disables disambiguation
     * @param query request query processing
     */
    public void setQuery(Boolean query) {
        this.query = query;
    }

    /**
     * Set whether to use different tokenizers for different scripts.
     * If false, uses the tokenizer for the {@code defaultTokenizationLanguage}.
     * Applies only to statistical segmentation languages
     * @param tokenizeForScript whether to use different tokenizers for different scripts
     */
    public void setTokenizeForScript(Boolean tokenizeForScript) {
        this.tokenizeForScript = tokenizeForScript;
    }

    /**
     * set the minimum non-primary scripting region length
     * @param minNonPrimaryScriptRegionLength the minimum non-primary scripting region length
     */
    public void setMinNonPrimaryScriptRegionLength(Integer minNonPrimaryScriptRegionLength) {
        this.minNonPrimaryScriptRegionLength = minNonPrimaryScriptRegionLength;
    }

    /**
     * set whether to include Hebrew roots
     * @param includeHebrewRoots whether to include Hebrew roots
     */
    public void setIncludeHebrewRoots(Boolean includeHebrewRoots) {
        this.includeHebrewRoots = includeHebrewRoots;
    }

    /**
     * set whether to enable Unicode normalization before tokenization
     * @param nfkcNormalize whether to enable Unicode normalization before tokenization
     */
    public void setNfkcNormalize(Boolean nfkcNormalize) {
        this.nfkcNormalize = nfkcNormalize;
    }

    /**
     *  set whether to enable tokenization for supported languages
     * @param fstTokenize whether to enable tokenization for supported languages
     */
    public void setFstTokenize(Boolean fstTokenize) {
        this.fstTokenize = fstTokenize;
    }

    /**
     * set the default tokenization language
     * @param defaultTokenizationLanguage the default tokenization language
     */
    public void setDefaultTokenizationLanguage(String defaultTokenizationLanguage) {
        this.defaultTokenizationLanguage = defaultTokenizationLanguage;
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
     * if the param is a {@code MorphologyOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MorphologyOptions)) {
            return false;
        }

        MorphologyOptions that = (MorphologyOptions) o;
        return disambiguate != null ? disambiguate.equals(that.getDisambiguate()) : that.disambiguate == null
                && query != null ? query.equals(that.getQuery()) : that.query == null
                && tokenizeForScript != null ? tokenizeForScript.equals(that.getTokenizeForScript()) : that.tokenizeForScript == null
                && minNonPrimaryScriptRegionLength != null ? minNonPrimaryScriptRegionLength.equals(that.getMinNonPrimaryScriptRegionLength()) : that.minNonPrimaryScriptRegionLength == null
                && includeHebrewRoots != null ? includeHebrewRoots.equals(that.getIncludeHebrewRoots()) : that.includeHebrewRoots == null
                && nfkcNormalize != null ? nfkcNormalize.equals(that.getNfkcNormalize()) : that.nfkcNormalize == null
                && fstTokenize != null ? fstTokenize.equals(that.getNfkcNormalize()) : that.fstTokenize == null
                && defaultTokenizationLanguage != null ? defaultTokenizationLanguage.equals(that.getDefaultTokenizationLanguage()) : that.defaultTokenizationLanguage == null;
    }
}
