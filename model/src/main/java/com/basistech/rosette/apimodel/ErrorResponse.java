/*
* Copyright 2024 Basis Technology Corp.
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

import com.basistech.rosette.annotations.JacksonMixin;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Analytics API response error data
 */
@Data
@EqualsAndHashCode
@Builder
@JacksonMixin
public class ErrorResponse extends Response {

    /**
     * @return the error code
     */
    private String code;

    /**
     * @return the error message
     */
    private String message;
}
