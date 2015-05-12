package com.basistech.rosette.model;

import java.util.Set;

public final class LanguageOptions {

    private final Integer minValidChars;
    private final Integer profileDepth;
    private final Double ambiguityThreshold;
    private final Double invalidityThreshold;
    private final String languageHint;
    private final Double languageHintWeight;
    private final String encodingHint;
    private final Double encodingHintWeight;
    private final Set<LanguageWeight> languageWeightAdjustments;

    public LanguageOptions(
            Integer minValidChars,
            Integer profileDepth,
            Double ambiguityThreshold,
            Double invalidityThreshold,
            String languageHint,
            Double languageHintWeight,
            String encodingHint,
            Double encodingHintWeight,
            Set<LanguageWeight> languageWeightAdjustments
    ) {
        this.minValidChars = minValidChars;
        this.profileDepth = profileDepth;
        this.ambiguityThreshold = ambiguityThreshold;
        this.invalidityThreshold = invalidityThreshold;
        this.languageHint = languageHint;
        this.languageHintWeight = languageHintWeight;
        this.encodingHint = encodingHint;
        this.encodingHintWeight = encodingHintWeight;
        this.languageWeightAdjustments = languageWeightAdjustments;
    }

    public Integer getMinValidChars() {
        return minValidChars;
    }

    public Integer getProfileDepth() {
        return profileDepth;
    }

    public Double getAmbiguityThreshold() {
        return ambiguityThreshold;
    }

    public Double getInvalidityThreshold() {
        return invalidityThreshold;
    }

    public String getLanguageHint() {
        return languageHint;
    }

    public Double getLanguageHintWeight() {
        return languageHintWeight;
    }

    public String getEncodingHint() {
        return encodingHint;
    }

    public Double getEncodingHintWeight() {
        return encodingHintWeight;
    }

    public Set<LanguageWeight> getLanguageWeightAdjustments() {
        return languageWeightAdjustments;
    }
}
