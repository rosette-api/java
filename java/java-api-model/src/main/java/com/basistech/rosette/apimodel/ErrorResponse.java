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

/**
 * Rosette API response error data
 */
public final class ErrorResponse extends Response {

    private final String code;
    private final String message;

    /**
     * constructor for {@code ErrorResponse}
     * @param requestId request id
     * @param code error code
     * @param message error message
     */
    public ErrorResponse(String requestId,
                         String code,
                         String message) {
        super(requestId);
        this.code = code;
        this.message = message;
    }

    /**
     * get the error code
     * @return the error code
     */
    public String getCode() {
        return code;
    }

    /**
     * get the error message
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code ErrorResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ErrorResponse)) {
            return false;
        }

        ErrorResponse that = (ErrorResponse) o;
        return super.equals(o)
                && code != null ? code.equals(that.getCode()) : that.code == null
                && message != null ? message.equals(that.getMessage()) : that.message == null;
    }
}
