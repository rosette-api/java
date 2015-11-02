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
        return this.getClass().getSimpleName() + " http status code: " + httpStatusCode
                + ", requestId: " + requestId + ", code: " + code + ", message: " + message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
