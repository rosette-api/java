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
 * Simple api response data model for Rosette Name Translator
 */
public final class BatchNameTranslationResponse extends Response {
    
    private List<TranslatedNameResult> results;

    public BatchNameTranslationResponse() { super(); }

    /**
     * Constructor for {@code BatchNameTranslationResponse}
     * @param requestId request id
     * @param results list of translated name results
     */
    public BatchNameTranslationResponse(String requestId,
                                        List<TranslatedNameResult> results) {
        super(requestId);
        this.results = results;
    }

    /**
     * Get the list of translated name results
     * @return the list of translated name results
     */
    public List<TranslatedNameResult> getResults() {
        return results;
    }
}