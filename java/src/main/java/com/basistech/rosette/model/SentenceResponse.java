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
 * Simple api response data model for sentence determination requests 
 */
public final class SentenceResponse extends Response {

    private List<String> sentences;

    public SentenceResponse() { super(); }

    /**
     * constructor for {@code SentenceResponse}
     * @param requestId request id
     * @param sentences list of sentences
     */
    public SentenceResponse(
            String requestId,
            List<String> sentences) {
        super(requestId);
        this.sentences = sentences;
    }

    /**
     * get the list of sentences
     * @return the list of sentences
     */
    public List<String> getSentences() {
        return sentences;
    }
}
