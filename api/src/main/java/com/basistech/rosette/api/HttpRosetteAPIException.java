/*
* Copyright 2016 Basis Technology Corp.
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
package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.CommonRosetteAPIException;
import com.basistech.rosette.apimodel.ErrorResponse;

/**
 * Exception from {@link HttpRosetteAPI}
 */
public class HttpRosetteAPIException extends CommonRosetteAPIException {
    private final Integer httpStatusCode;

    public HttpRosetteAPIException(ErrorResponse errorResponse, Integer httpStatusCode) {
        super(errorResponse);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpRosetteAPIException(String message, Throwable cause, ErrorResponse errorResponse, Integer httpStatusCode) {
        super(message, cause, errorResponse);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpRosetteAPIException(String message, ErrorResponse errorResponse, Integer httpStatusCode) {
        super(message, errorResponse);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpRosetteAPIException(Throwable cause, ErrorResponse errorResponse, Integer httpStatusCode) {
        super(cause, errorResponse);
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * @return the HTTP status code associated with this error, or {@code null} if there was no HTTP error involved.
     */
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
