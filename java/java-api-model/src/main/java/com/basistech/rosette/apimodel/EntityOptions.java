/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import java.util.EnumSet;

/**
 * Entity options 
 */
public final class EntityOptions {

    public static final EntityOptions DEFAULT_OPTIONS = new EntityOptions(true, 8, EnumSet.allOf(ProcessorType.class), false, false);

    private final Boolean resolveNamedEntities;
    private final Integer maxEntityTokens;
    private final EnumSet<ProcessorType> processors;
    private final Boolean allowPartialGazetteerMatches;
    private final Boolean redactorPreferLength;

    /**
     * Constructor for {@code EntityOptions}
     * @param resolveNamedEntities resolve in-document named entities
     * @param maxEntityTokens max number of tokens allowed in an entity
     * @param processors get the set of active processors
     * @param allowPartialGazetteerMatches whether to allow partial gazetteer matches
     * @param redactorPreferLength whether to prefer length over weights for redaction
     */
    public EntityOptions(
            Boolean resolveNamedEntities,
            Integer maxEntityTokens,
            EnumSet<ProcessorType> processors,
            Boolean allowPartialGazetteerMatches,
            Boolean redactorPreferLength) {
        this.resolveNamedEntities = resolveNamedEntities;
        this.maxEntityTokens = maxEntityTokens;
        this.processors = processors;
        this.allowPartialGazetteerMatches = allowPartialGazetteerMatches;
        this.redactorPreferLength = redactorPreferLength;
    }

    /**
     * get the resolve in-document named entities
     * @return the resolve in-document named entities
     */
    public Boolean getResolveNamedEntities() {
        return resolveNamedEntities;
    }

    /**
     * get the max number of tokens allowed in an entity
     * @return the max number of tokens allowed in an entity
     */
    public Integer getMaxEntityTokens() {
        return maxEntityTokens;
    }

    /**
     * get the set of active processors 
     * @return the set of active processors
     */
    public EnumSet<ProcessorType> getProcessors() {
        return processors;
    }

    /**
     * get whether to allow partial gazetteer matches 
     * @return whether to allow partial gazetteer matches
     */
    public Boolean getAllowPartialGazetteerMatches() {
        return allowPartialGazetteerMatches;
    }

    /**
     * get whether to prefer length over weights for redaction 
     * @return whether to prefer length over weights for redaction
     */
    public Boolean getRedactorPreferLength() {
        return redactorPreferLength;
    }

    @Override
    public int hashCode() {
        int result = resolveNamedEntities != null ? resolveNamedEntities.hashCode() : 0;
        result = 31 * result + (maxEntityTokens != null ? maxEntityTokens.hashCode() : 0);
        result = 31 * result + (processors != null ? processors.hashCode() : 0);
        result = 31 * result + (allowPartialGazetteerMatches != null ? allowPartialGazetteerMatches.hashCode() : 0);
        result = 31 * result + (redactorPreferLength != null ? redactorPreferLength.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code EntityOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof EntityOptions) {
            EntityOptions that = (EntityOptions) o;
            return resolveNamedEntities != null ? resolveNamedEntities.equals(that.getResolveNamedEntities()) : that.resolveNamedEntities == null
                    && maxEntityTokens != null ? maxEntityTokens.equals(that.getMaxEntityTokens()) : that.maxEntityTokens == null
                    && processors != null ? processors.equals(that.getProcessors()) : that.processors == null
                    && allowPartialGazetteerMatches != null ? allowPartialGazetteerMatches.equals(that.getAllowPartialGazetteerMatches()) : that.allowPartialGazetteerMatches == null
                    && redactorPreferLength != null ? redactorPreferLength.equals(that.getRedactorPreferLength()) : that.redactorPreferLength == null;
        } else {
            return false;
        }
    }
}
