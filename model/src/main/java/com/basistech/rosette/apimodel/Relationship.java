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

/**
 * Relationship extracted by the relationship extractor
 */
public final class Relationship {

    private final RelationshipPredicate predicate;
    private final RelationshipArgument arg1;
    private final RelationshipArgument arg2;
    private final RelationshipArgument arg3;
    private final List<RelationshipAdjunct> adjuncts;
    private final String source;
    private final String context;
    private final Double confidence;

    /**
     * constructor for {@code Relationship}
     * @param predicate relationship predicate
     * @param arg1 relationship argument 1
     * @param arg2 relationship argument 2
     * @param arg3 relationship argument 3
     * @param adjuncts relationship adjuncts
     * @param source relationship argument type
     * @param context relationship context
     * @param confidence  a measure of quality of relationship extraction
     */
    public Relationship(
            RelationshipPredicate predicate,
            RelationshipArgument arg1,
            RelationshipArgument arg2,
            RelationshipArgument arg3,
            List<RelationshipAdjunct> adjuncts,
            String source,
            String context,
            Double confidence) {
        this.predicate = predicate;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.adjuncts = adjuncts;
        this.source = source;
        this.context = context;
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
        result = 31 * result + (adjuncts != null ? adjuncts.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
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
                && adjuncts != null ? adjuncts.equals(that.getAdjuncts()) : that.adjuncts == null
                && source != null ? source.equals(that.getSource()) : that.source == null
                && context != null ? context.equals(that.getContext()) : that.context == null
                && confidence == that.getConfidence();
    }

    /**
     * get the relationship predicate
     * @return the relationship predicate
     */
    public RelationshipPredicate getPredicate() {
        return predicate;
    }

    /**
     * @return the first arg.
     */
    public RelationshipArgument getArg1() {
        return arg1;
    }

    /**
     * @return the second arg.
     */
    public RelationshipArgument getArg2() {
        return arg2;
    }

    /**
     * @return the third arg.
     */
    public RelationshipArgument getArg3() {
        return arg3;
    }

    /**
     * get a list of adjuncts
     * @return a list of adjuncts
     */
    public List<RelationshipAdjunct> getAdjuncts() {
        return adjuncts;
    }

    /**
     * get the source
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * get the context
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * get the confidence
     * @return the confidence
     */
    public Double getConfidence() {
        return confidence;
    }
}
