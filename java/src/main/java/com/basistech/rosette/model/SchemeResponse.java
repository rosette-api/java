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
 * Response data for schemes
 */
public final class SchemeResponse extends Response {

    private List<String> schemes;

    public SchemeResponse() { super(); }

    /**
     * constructor for {@code SchemeResponse}
     * @param requestId request id
     * @param schemes list of schemes
     */
    public SchemeResponse(String requestId,
                          List<String> schemes) {
        super(requestId);
        this.schemes = schemes;
    }

    /**
     * get the list of schemes
     * @return the list of schemes
     */
    public List<String> getSchemes() {
        return schemes;
    }
}