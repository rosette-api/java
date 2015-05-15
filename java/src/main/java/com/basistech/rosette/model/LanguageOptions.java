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
