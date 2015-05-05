package com.basistech.rosette.model;

/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

public class ErrorResponse extends Response {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String requestId,
                         String code,
                         String message) {
        super(requestId);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return super.toString() + ", code: " + code + ", message: " + message;
    }
}
