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

import java.util.List;

/**
 * Relationship extracted by the relationship extractor
 */
public final class Relationship {

    private final String predicate;
    private final String arg1;
    private final String arg2;
    private final String arg3;
    private final List<String> temporals;
    private final List<String> locatives;
    private final List<String> adjuncts;
    private final Double confidence;

    /**
     * constructor for {@code Relationship}
     * @param predicate relationship predicate
     * @param arg1 relationship argument 1
     * @param arg2 relationship argument 2
     * @param arg3 relationship argument 3
     * @param temporals relationship temporals
     * @param locatives relationship locatives
     * @param adjuncts relationship adjuncts
     * @param confidence  a measure of quality of relationship extraction
     */
    public Relationship(
            String predicate,
            String arg1,
            String arg2,
            String arg3,
            List<String> temporals,
            List<String> locatives,
            List<String> adjuncts,
            Double confidence) {
        this.predicate = predicate;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.temporals = temporals;
        this.locatives = locatives;
        this.adjuncts = adjuncts;
        this.confidence = confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = predicate != null ? predicate.hashCode() : 0;
        result = 31 * result + (arg1 != null ? arg1.hashCode() : 0);
        result = 31 * result + (arg2 != null ? arg2.hashCode() : 0);
        result = 31 * result + (arg3 != null ? arg3.hashCode() : 0);
        result = 31 * result + (temporals != null ? temporals.hashCode() : 0);
        result = 31 * result + (locatives != null ? locatives.hashCode() : 0);
        result = 31 * result + (adjuncts != null ? adjuncts.hashCode() : 0);
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
                && arg1 != null ? arg1.equals(that.getArg1()) : that.arg1 == null
                && arg2 != null ? arg2.equals(that.getArg2()) : that.arg2 == null
                && arg3 != null ? arg3.equals(that.getArg3()) : that.arg3 == null
                && temporals != null ? temporals.equals(that.getTemporals()) : that.temporals == null
                && locatives != null ? locatives.equals(that.getLocatives()) : that.locatives == null
                && adjuncts != null ? adjuncts.equals(that.getAdjuncts()) : that.adjuncts == null
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
     * @return
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * @return
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * @return
     */
    public String getArg3() {
        return arg3;
    }

    /**
     * get a list of temporals
     * @return a list of temporals
     */
    public List<String> getTemporals() {
        return temporals;
    }

    /**
     * get a list of locatives
     * @return a list of locatives
     */
    public List<String> getLocatives() {
        return locatives;
    }

    /**
     * get a list of adjuncts
     * @return a list of adjuncts
     */
    public List<String> getAdjuncts() {
        return adjuncts;
    }

    /**
     * get the confidence
     * @return the confidence
     */
    public Double getConfidence() {
        return confidence;
    }
}
