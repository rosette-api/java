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

import java.util.List;

/**
 * Simple api response data model for Rli 
 */
public final class LanguageResponse extends Response {

    private List<LanguageDetectionResult> languageDetections;

    public LanguageResponse() { super(); }

    /**
     * constructor for {@code LanguageResponse}
     * @param requestId request id
     * @param languageDetections list of detected languages
     */
    public LanguageResponse(String requestId,
                            List<LanguageDetectionResult> languageDetections) {
        super(requestId);
        this.languageDetections = languageDetections;
    }

    /**
     * get the list of detected languages
     * @return the list of detected languages
     */
    public List<LanguageDetectionResult> getLanguageDetections() {
        return languageDetections;
    }
}
