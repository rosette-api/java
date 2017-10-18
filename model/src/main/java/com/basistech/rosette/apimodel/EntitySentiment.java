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

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Per-entity sentiment info.
 */
@SuppressWarnings("PMD")
@Getter @EqualsAndHashCode
public final class EntitySentiment extends EntityMention {

    /**
     * @return the sentiment information.
     */
    private final Label sentiment;

    /**
     * constructor for {@code EntitySentiment}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param confidence entity confidence.
     * @param sentiment the sentiment information.
     */
    @Deprecated
    public EntitySentiment(String type,
                           String mention,
                           String normalized,
                           Integer count,
                           String entityId,
                           Double confidence,
                           Label sentiment) {
        this(type, mention, normalized, count, null, entityId, confidence, null, sentiment);
    }

    /**
     * constructor for {@code EntitySentiment}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param mentionOffsets mention offsets
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param confidence entity confidence.
     * @param sentiment the sentiment information.
     */
    public EntitySentiment(String type,
                           String mention,
                           String normalized,
                           Integer count,
                           List<MentionOffsets> mentionOffsets,
                           String entityId,
                           Double confidence,
                           Double salience,
                           Label sentiment) {
        super(type, mention, normalized, count, mentionOffsets, entityId, confidence, salience);
        this.sentiment = sentiment;
    }
}
