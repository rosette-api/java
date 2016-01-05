/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel;

/**
 * Rosette api response data base
 */
public abstract class Response {

    private String requestId;

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
    public String getRequestId() {
        return requestId;
    }

    @Override
    public int hashCode() {
        return requestId != null ? requestId.hashCode() : 0;
    }

    /**
     * if the param is a response, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object; t
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Response)) {
            return false;
        }

        Response that = (Response) o;
        return requestId != null ? requestId.equals(that.getRequestId()) : that.requestId == null;
    }
}
