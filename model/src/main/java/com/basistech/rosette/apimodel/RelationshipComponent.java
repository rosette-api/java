/*
* Copyright 2014 Basis Technology Corp.
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
 * Relationship extracted by the relationship extractor
 */
public abstract class RelationshipComponent {

    protected final String label;
    protected final int startOffset;
    protected final int endOffset;

    protected RelationshipComponent(
            String label,
            int startOffset,
            int endOffset
    ) {
        this.label = label;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    /**
     * get the label
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get the start offset
     * @return the startOffset
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * get the end offset
     * @return the endOffset
     */
    public int getEndOffset() {
        return endOffset;
    }

    public int hashCode() {
        return Objects.hash(label,
                startOffset,
                endOffset);
    }
}
