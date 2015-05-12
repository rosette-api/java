package com.basistech.rosette.model;

public final class LinguisticsOptions {

    private final Boolean disambiguate;
    private final Boolean query;
    private final Boolean tokenizeForScript;
    private final Integer minNonPrimaryScriptRegionLength;
    private final Boolean includeHebrewRoots;
    private final Boolean nfkcNormalize;
    private final Boolean fstTokenize;
    private final String defaultTokenizationLanguage;

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

    public Boolean getDisambiguate() {
        return disambiguate;
    }

    public Boolean getQuery() {
        return query;
    }

    public Boolean getTokenizeForScript() {
        return tokenizeForScript;
    }

    public Integer getMinNonPrimaryScriptRegionLength() {
        return minNonPrimaryScriptRegionLength;
    }

    public Boolean getIncludeHebrewRoots() {
        return includeHebrewRoots;
    }

    public Boolean getNfkcNormalize() {
        return nfkcNormalize;
    }

    public Boolean getFstTokenize() {
        return fstTokenize;
    }

    public String getDefaultTokenizationLanguage() {
        return defaultTokenizationLanguage;
    }
}
