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
 * Rosette API response error data
 */
public class ErrorResponse extends Response {
    private String code;
    private String message;
    
    public ErrorResponse() { }

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
}
