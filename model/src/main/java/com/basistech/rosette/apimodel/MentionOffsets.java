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

import java.util.Objects;

/**
 * The start and end offset/index for a given mention in a string of text
 */
public class MentionOffsets {

    private Integer startOffset;
    private Integer endOffset;

    /**
     * constructor for {@code MentionOffsets}
     * @param startOffset offset for the first character of a mention
     * @param endOffset offset for the last character of a mention
     */
    public MentionOffsets(Integer startOffset, Integer endOffset) {
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    /**
     * @returns the offset for the first character of a mention, based on UTF-16 code page
     */
    public Integer getStartOffset() {
        return startOffset;
    }

    /**
     * @returns the offset for the last character of a mention, based on UTF-16 code page
     */
    public Integer getEndOffset() {
        return endOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MentionOffsets that = (MentionOffsets) o;
        return Objects.equals(startOffset, that.startOffset)
                && Objects.equals(endOffset, that.endOffset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startOffset, endOffset);
    }
}
