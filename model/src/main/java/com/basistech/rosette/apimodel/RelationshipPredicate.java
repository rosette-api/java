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
 *
 */
public class RelationshipPredicate extends RelationshipComponent {

    private final String predicateId;

    /**
     * constructor for {@code RelationshipPredicate}
     * @param label label
     * @param startOffset start offset
     * @param endOffset end offset
     * @param predicateId predicate id
     */
    public RelationshipPredicate(
            String label,
            int startOffset,
            int endOffset,
            String predicateId
    ) {
        super(label, startOffset, endOffset);
        this.predicateId = predicateId;
    }

    /**
     * get the predicate ID
     * @return predicateId
     */
    public String getPredicateId() {
        return predicateId;
    }

    @Override
    public int hashCode() {
        return 31 * predicateId.hashCode()
                + super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipPredicate that = (RelationshipPredicate) o;
        return Objects.equals(label, that.label)
                && Objects.equals(startOffset, that.startOffset)
                && Objects.equals(endOffset, that.endOffset)
                && Objects.equals(predicateId, that.predicateId);
    }
}
