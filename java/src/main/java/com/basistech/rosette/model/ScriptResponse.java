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
 * Response data model for scripts 
 */
public final class ScriptResponse extends Response {

    private List<String> scripts;

    public ScriptResponse() { super(); }

    /**
     * constructor for {@code ScriptResponse}
     * @param requestId request id
     * @param scripts list of scripts
     */
    public ScriptResponse(String requestId,
                          List<String> scripts) {
        super(requestId);
        this.scripts = scripts;
    }

    /**
     * get the list of scripts
     * @return
     */
    public List<String> getScripts() {
        return scripts;
    }
}