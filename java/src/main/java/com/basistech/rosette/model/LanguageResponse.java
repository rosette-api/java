package com.basistech.rosette.model;
/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

import java.util.List;

/* Simple api response data model for Rli */

public final class LanguageResponse extends Response {

    private List<LanguageDetectionResult> languageDetections;

    public LanguageResponse() {
    }

    public LanguageResponse(String requestId,
                            List<LanguageDetectionResult> languageDetections) {
        super(requestId);
        this.languageDetections = languageDetections;
    }

    // Getter
    public List<LanguageDetectionResult> getLanguageDetections() {
        return languageDetections;
    }

    public String toString() {
        String result = super.toString() + ", ";
        for (LanguageDetectionResult ldr : languageDetections) {
            result += ldr.getLanguage() + ": " + ldr.getConfidence() + ", ";
        }
        return result;
    }
}
