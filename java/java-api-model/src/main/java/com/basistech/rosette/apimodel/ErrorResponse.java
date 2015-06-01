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

/**
 * Rosette API response error data
 */
public class ErrorResponse extends Response {
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
        if (o instanceof ErrorResponse) {
            ErrorResponse that = (ErrorResponse) o;
            return super.equals(o)
                    && code != null ? code.equals(that.getCode()) : code == that.getCode()
                    && message != null ? message.equals(that.getMessage()) : message == that.getMessage();
        } else {
            return false;
        }
    }
}
