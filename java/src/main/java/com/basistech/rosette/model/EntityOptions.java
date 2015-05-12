package com.basistech.rosette.model;

import java.util.EnumSet;

public final class EntityOptions {
    private final Boolean resolveNamedEntities;
    private final Integer maxEntityTokens;
    private final Boolean allowPartialGazetteerMatches;
    private final Boolean redactorPreferLength;
    private final EnumSet<ProcessorType> processors;

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

    public Boolean getResolveNamedEntities() {
        return resolveNamedEntities;
    }

    public Integer getMaxEntityTokens() {
        return maxEntityTokens;
    }

    public EnumSet<ProcessorType> getProcessors() {
        return processors;
    }

    public Boolean getAllowPartialGazetteerMatches() {
        return allowPartialGazetteerMatches;
    }

    public Boolean getRedactorPreferLength() {
        return redactorPreferLength;
    }
}
