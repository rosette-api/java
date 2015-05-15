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

/**
 * Data from constant tickets
 */
public class ConstantsResponse extends Response {

    //CHECKSTYLE:OFF
    public String version;
    public String build;
    public Object support;
    //CHECKSTYLE:ON
    public ConstantsResponse() { super(); }

    /**
     * Constructor for {@code ConstantsResponse}
     * @param requestId request id
     * @param version component version
     * @param build component build
     * @param support support
     */
    public ConstantsResponse(String requestId, String version, String build, Object support) {
        super(requestId);
        this.version = version;
        this.build = build;
        this.support = support;
    }
}
