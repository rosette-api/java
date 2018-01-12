/*
* Copyright 2017 Basis Technology Corp.
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
package com.basistech.rosette.apimodel.batch;

import com.basistech.rosette.annotations.JacksonMixin;
import com.basistech.rosette.apimodel.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
@JacksonMixin
public final class BatchResponse extends Response {
    // batch id
    private final String id;
    // URL where the batch results will be stored
    private final String batchOutputUrl;
    // progress checking endpoint/url
    private final String batchCheckProgressUrl;
}
