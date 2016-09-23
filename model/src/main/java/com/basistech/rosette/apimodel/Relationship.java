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

import java.util.List;
import java.util.Set;

/**
 * Relationship extracted by the relationship extractor
 */
public final class Relationship {

    private final String predicate;
    private final String predicateId;
    private final String arg1;
    private final String arg1Id;
    private final String arg2;
    private final String arg2Id;
    private final String arg3;
    private final String arg3Id;
    private final List<String> adjuncts;
    private final Set<String> modalities;
    private final String source;
    private final Double confidence;

    /**
     * constructor for {@code Relationship}
     * @param predicate relationship predicate
     * @param predicateId
     * @param arg1 relationship argument 1
     * @param arg1Id
     * @param arg2 relationship argument 2
     * @param arg2Id
     * @param arg3 relationship argument 3
     * @param arg3Id
     * @param adjuncts relationship adjuncts
     * @param modalities
     * @param source
     * @param confidence  a measure of quality of relationship extraction
     */
    public Relationship(
            String predicate,
            String predicateId,
            String arg1,
            String arg1Id,
            String arg2,
            String arg2Id,
            String arg3,
            String arg3Id,
            List<String> adjuncts,
            Set<String> modalities,
            String source,
            Double confidence) {
        this.predicate = predicate;
        this.predicateId = predicateId;
        this.arg1 = arg1;
        this.arg1Id = arg1Id;
        this.arg2 = arg2;
        this.arg2Id = arg2Id;
        this.arg3 = arg3;
        this.arg3Id = arg3Id;
        this.adjuncts = adjuncts;
        this.modalities = modalities;
        this.source = source;
        this.confidence = confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = predicate != null ? predicate.hashCode() : 0;
        result = 31 * result + (predicateId != null ? predicateId.hashCode() : 0);
        result = 31 * result + (arg1 != null ? arg1.hashCode() : 0);
        result = 31 * result + (arg1Id != null ? arg1Id.hashCode() : 0);
        result = 31 * result + (arg2 != null ? arg2.hashCode() : 0);
        result = 31 * result + (arg2Id != null ? arg2Id.hashCode() : 0);
        result = 31 * result + (arg3 != null ? arg3.hashCode() : 0);
        result = 31 * result + (arg3Id != null ? arg3Id.hashCode() : 0);
        result = 31 * result + (adjuncts != null ? adjuncts.hashCode() : 0);
        result = 31 * result + (modalities != null ? modalities.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * if the param is a {@code Relationship}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Relationship)) {
            return false;
        }

        Relationship that = (Relationship) o;
        return predicate != null ? predicate.equals(that.getPredicate()) : that.predicate == null
                && predicateId != null ? predicateId.equals(that.getPredicateId()) : that.predicateId == null
                && arg1 != null ? arg1.equals(that.getArg1()) : that.arg1 == null
                && arg1Id != null ? arg1Id.equals(that.getArg1Id()) : that.arg1Id == null
                && arg2 != null ? arg2.equals(that.getArg2()) : that.arg2 == null
                && arg2Id != null ? arg2Id.equals(that.getArg2Id()) : that.arg2Id == null
                && arg3 != null ? arg3.equals(that.getArg3()) : that.arg3 == null
                && arg3Id != null ? arg3Id.equals(that.getArg3Id()) : that.arg3Id == null
                && adjuncts != null ? adjuncts.equals(that.getAdjuncts()) : that.adjuncts == null
                && source != null ? source.equals(that.getSource()) : that.source == null
                && modalities != null ? modalities.equals(that.getModalities()) : that.modalities == null
                && confidence == that.getConfidence();
    }

    /**
     * get the relationship predicate
     * @return the relationship predicate
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     *
     * @return predicate id
     */
    public String getPredicateId() {
        return predicateId;
    }

    /**
     * @return the first arg.
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * @return the first arg id
     */
    public String getArg1Id() {
        return arg1Id;
    }

    /**
     * @return the second arg.
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * @return the second ard id
     */
    public String getArg2Id() {
        return arg2Id;
    }

    /**
     * @return the third arg.
     */
    public String getArg3() {
        return arg3;
    }

    /**
     * @return the third arg id
     */
    public String getArg3Id() {
        return arg3Id;
    }

    /**
     * get a list of adjuncts
     * @return a list of adjuncts
     */
    public List<String> getAdjuncts() {
        return adjuncts;
    }

    /**
     * @return modalities
     */
    public Set<String> getModalities() {
        return modalities;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * get the confidence
     * @return the confidence
     */
    public Double getConfidence() {
        return confidence;
    }
}
