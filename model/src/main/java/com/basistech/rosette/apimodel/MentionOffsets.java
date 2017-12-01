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

package com.basistech.rosette.apimodel;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;

/**
 * The start and end offset/index for a given mention in a string of text
 */
@Value
@Builder
public class MentionOffsets {

    /**
     * @return the offset for the first character of a mention, based on UTF-16 code page
     */
    @Min(0)
    private final Integer startOffset;

    /**
     * @return the offset for the last character of a mention, based on UTF-16 code page
     */
    @Min(0)
    private final Integer endOffset;

}
