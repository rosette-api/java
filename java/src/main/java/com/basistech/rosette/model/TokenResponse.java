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
 * Simple api response data model for tokenization requests 
 */
public final class TokenResponse extends Response {

    private List<String> tokens;
    
    public TokenResponse() { super(); }

    /**
     * constructor for {@code TokenResponse}
     * @param requestId request id
     * @param tokens list of tokens
     */
    public TokenResponse(
            String requestId,
            List<String> tokens) {
        super(requestId);
        this.tokens = tokens;
    }

    /**
     * get the list of tokens
     * @return the list of tokens
     */
    public List<String> getTokens() {
        return tokens;
    }
}
