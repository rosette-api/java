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

package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.ErrorResponse;

public class RosetteAPIException extends Exception {

    private final int httpStatusCode;
    private final String requestId;
    private final String code;
    private final String message;

    public RosetteAPIException(int httpStatusCode, ErrorResponse response) {
        super();
        this.httpStatusCode = httpStatusCode;
        code = response.getCode();
        message = response.getMessage();
        requestId = response.getRequestId();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getRequestId() {
        return requestId;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " http status code: " + httpStatusCode + ", requestId: " + requestId + ", code: " + code + ", message: " + message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
