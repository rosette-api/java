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
 * Simple api response data model for name translation
 */
public final class NameTranslationResponse extends Response {
    private final TranslatedNameResult result;
    
    /**
     * constructor for {@code NameTranslationResponse}
     * @param requestId request id
     * @param result name translation result
     */
    public NameTranslationResponse(String requestId,
                                   TranslatedNameResult result) {
        super(requestId);
        this.result = result;
    }

    /**
     * get the name translation result
     * @return the name translation result
     */
    public TranslatedNameResult getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    /**
     * if the param is a {@code NameTranslationResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof NameTranslationResponse) {
            NameTranslationResponse that = (NameTranslationResponse) o;
            return super.equals(o)
                    && result != null ? result.equals(that.getResult()) : result == that.getResult();
        } else {
            return false;
        }
    }
}