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

import java.util.Objects;

/**
 * Per-entity sentiment info.
 */
public class EntitySentiment {
    private final String mention;
    private final String type;
    private final String entityId;
    private final Label label;

    public EntitySentiment(String mention, String type, String entityId, Label label) {
        this.mention = mention;
        this.type = type;
        this.entityId = entityId;
        this.label = label;
    }

    public String getMention() {
        return mention;
    }

    public String getType() {
        return type;
    }

    public String getEntityId() {
        return entityId;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntitySentiment that = (EntitySentiment) o;
        return Objects.equals(mention, that.mention)
                && Objects.equals(type, that.type)
                && Objects.equals(entityId, that.entityId)
                && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mention, type, entityId, label);
    }
}
