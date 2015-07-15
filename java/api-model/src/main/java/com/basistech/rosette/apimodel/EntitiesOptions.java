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
 * Entity extraction options
 */
public final class EntitiesOptions {

    public static final EntitiesOptions DEFAULT_OPTIONS = new EntitiesOptions(true, 8, EnumSet.allOf(ExtractionMethod.class), false, false);

    private Boolean resolveNamedEntities;
    private Integer maxEntityTokens;
    private EnumSet<ExtractionMethod> extractionMethods;
    private Boolean allowPartialGazetteerMatches;
    private Boolean redactorPreferLength;

    /**
     * Constructor for {@code EntitiesOptions}
     * @param resolveNamedEntities resolve in-document named entities
     * @param maxEntityTokens max number of tokens allowed in an entity
     * @param extractionMethods get the set of active extraction methods
     * @param allowPartialGazetteerMatches whether to allow partial gazetteer matches
     * @param redactorPreferLength whether to prefer length over weights for redaction
     */
    public EntitiesOptions(
            Boolean resolveNamedEntities,
            Integer maxEntityTokens,
            EnumSet<ExtractionMethod> extractionMethods,
            Boolean allowPartialGazetteerMatches,
            Boolean redactorPreferLength) {
        this.resolveNamedEntities = resolveNamedEntities;
        this.maxEntityTokens = maxEntityTokens;
        this.extractionMethods = extractionMethods;
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
     * get the set of active extraction methods
     * @return the set of active extraction methods
     */
    public EnumSet<ExtractionMethod> getExtractionMethods() {
        return extractionMethods;
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
     * set the set of active extraction methods
     * @param ExtractionMethods the set of active extraction methods
     */
    public void setExtractionMethods(EnumSet<ExtractionMethod> ExtractionMethods) {
        this.extractionMethods = ExtractionMethods;
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
        result = 31 * result + (extractionMethods != null ? extractionMethods.hashCode() : 0);
        result = 31 * result + (allowPartialGazetteerMatches != null ? allowPartialGazetteerMatches.hashCode() : 0);
        result = 31 * result + (redactorPreferLength != null ? redactorPreferLength.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code EntitiesOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntitiesOptions)) {
            return false;
        }

        EntitiesOptions that = (EntitiesOptions) o;
        return resolveNamedEntities != null ? resolveNamedEntities.equals(that.getResolveNamedEntities()) : that.resolveNamedEntities == null
                && maxEntityTokens != null ? maxEntityTokens.equals(that.getMaxEntityTokens()) : that.maxEntityTokens == null
                && extractionMethods != null ? extractionMethods.equals(that.getExtractionMethods()) : that.extractionMethods == null
                && allowPartialGazetteerMatches != null ? allowPartialGazetteerMatches.equals(that.getAllowPartialGazetteerMatches()) : that.allowPartialGazetteerMatches == null
                && redactorPreferLength != null ? redactorPreferLength.equals(that.getRedactorPreferLength()) : that.redactorPreferLength == null;
    }
}
