/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
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
        if (!(o instanceof Response)) {
            return false;
        }

        Response that = (Response) o;
        return requestId != null ? requestId.equals(that.getRequestId()) : that.requestId == null
                && timers != null ? timers.equals(that.getTimers()) : that.timers == null;
    }
}
