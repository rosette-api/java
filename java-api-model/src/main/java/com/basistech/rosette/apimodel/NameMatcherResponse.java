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
 * Response data model for name matcher
 */
public final class NameMatcherResponse extends Response {
    private NameMatcherResult result;

    /**
     * Constructor for {@code NameMatcherResponse}
     * @param requestId request id
     * @param result name matcher result
     */
    public NameMatcherResponse(String requestId,
                               NameMatcherResult result
    ) {
        super(requestId);
        this.result = result;
    }

    /**
     * Gets the name matcher result
     * @return name matcher result
     */
    public NameMatcherResult getResult() {
        return result;
    }
}