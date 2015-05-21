/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.model;

import java.util.EnumSet;

/**
 * Entity options 
 */
public final class EntityOptions {
    private Boolean resolveNamedEntities = true;
    private Integer maxEntityTokens = 8;
    private EnumSet<ProcessorType> processors = EnumSet.allOf(ProcessorType.class);
    private Boolean allowPartialGazetteerMatches;
    private Boolean redactorPreferLength;

    public EntityOptions() { }

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

    /**
     * set the resolve in-document named entities 
     * @param resolveNamedEntities the resolve in-document named entities
     */
    public void setResolveNamedEntities(Boolean resolveNamedEntities) {
        this.resolveNamedEntities = resolveNamedEntities;
    }

    /**
     * set the max number of tokens allowed in an entity 
     * @param maxEntityTokens the max number of tokens allowed in an entity
     */
    public void setMaxEntityTokens(Integer maxEntityTokens) {
        this.maxEntityTokens = maxEntityTokens;
    }

    /**
     * set the set of active processors 
     * @param processors the set of active processors
     */
    public void setProcessors(EnumSet<ProcessorType> processors) {
        this.processors = processors;
    }

    /**
     * set whether to allow partial gazetteer matches 
     * @param allowPartialGazetteerMatches whether to allow partial gazetteer matches
     */
    public void setAllowPartialGazetteerMatches(Boolean allowPartialGazetteerMatches) {
        this.allowPartialGazetteerMatches = allowPartialGazetteerMatches;
    }

    /**
     * set whether to prefer length over weights for redaction 
     * @param redactorPreferLength whether to prefer length over weights for redaction
     */
    public void setRedactorPreferLength(Boolean redactorPreferLength) {
        this.redactorPreferLength = redactorPreferLength;
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
            return resolveNamedEntities != null ? resolveNamedEntities.equals(that.getResolveNamedEntities()) : resolveNamedEntities == that.getResolveNamedEntities()
                    && maxEntityTokens != null ? maxEntityTokens.equals(that.getMaxEntityTokens()) : maxEntityTokens == that.getMaxEntityTokens()
                    && processors != null ? processors.equals(that.getProcessors()) : processors == that.getProcessors()
                    && allowPartialGazetteerMatches != null ? allowPartialGazetteerMatches.equals(that.getAllowPartialGazetteerMatches()) : allowPartialGazetteerMatches == that.getAllowPartialGazetteerMatches()
                    && redactorPreferLength != null ? redactorPreferLength.equals(that.getRedactorPreferLength()) : redactorPreferLength == that.getRedactorPreferLength();
        } else {
            return false;
        }
    }
}
