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
 * Response data model for scripts 
 */
public final class ScriptResponse extends Response {

    private List<String> scripts;

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
     * @return the list of scripts
     */
    public List<String> getScripts() {
        return scripts;
    }

    /**
     * set the list of scripts
     * @param scripts the list of scripts
     */
    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (scripts != null ? scripts.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code ScriptResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ScriptResponse) {
            ScriptResponse that = (ScriptResponse) o;
            return super.equals(o)
                    && scripts != null ? scripts.equals(that.getScripts()) : scripts == that.getScripts();
        } else {
            return false;
        }
    }
}