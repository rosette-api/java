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

import java.util.List;

/**
 * Simple api response data model for Rli 
 */
public final class LanguageResponse extends Response {

    private final List<LanguageDetectionResult> languageDetections;

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

    @Override
    public int hashCode() {
        return languageDetections != null ? languageDetections.hashCode() : 0;
    }

    /**
     * if the param is a {@code CategoryResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LanguageResponse) {
            LanguageResponse that = (LanguageResponse) o;
            return super.equals(o)
                    && languageDetections != null ? languageDetections.equals(that.getLanguageDetections()) : that.languageDetections == null;
        } else {
            return false;
        }
    }
}
