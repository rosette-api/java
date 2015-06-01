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

import java.util.Map;

/**
 * Rosette api response data base
 */
public abstract class Response {
    private String requestId;
    private Map<String, Long> timers;

    /**
     * abstract constructor for {@code Response}
     * @param requestId request id
     */
    public Response(String requestId) {
        this.requestId = requestId;
    }

    /**
     * get the request id for tracking purposes 
     * @return the request id
     */
    public String getRequestId() { return requestId; }

    /**
     * get the debug timing info 
     * @return the debug timing info
     */
    public Map<String, Long> getTimers() { return timers; }

    /**
     * set the timing info
     * @param timers the timing info
     */
    public void setTimers(Map<String, Long> timers) {
        this.timers = timers;
    }

    @Override
    public int hashCode() {
        int result = requestId != null ? requestId.hashCode() : 0;
        result = 31 * result + (timers != null ? timers.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a response, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Response) {
            Response that = (Response) o;
            return requestId != null ? requestId.equals(that.getRequestId()) : requestId == that.getRequestId()
                    && timers != null ? timers.equals(that.getTimers()) : timers == that.getTimers();
        } else {
            return false;
        }
    }
}
