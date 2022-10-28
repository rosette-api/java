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
package com.basistech.rosette.apimodel;

import com.basistech.rosette.RosetteRuntimeException;

/**
 * Exception from the Rosette API inherit from this exception.
 */
public class CommonRosetteAPIException extends RosetteRuntimeException {
    private final transient ErrorResponse errorResponse;

    public CommonRosetteAPIException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public CommonRosetteAPIException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    public CommonRosetteAPIException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public CommonRosetteAPIException(Throwable cause, ErrorResponse errorResponse) {
        super(cause);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
