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

import com.basistech.util.PartOfSpeechTagSet;

/**
 * Morphology options
 */
public final class MorphologyOptions extends Options {

    private Boolean disambiguate;
    private Boolean query;
    private PartOfSpeechTagSet partOfSpeechTagSet;

    /**
     * Create a set of morphology options with default values.
     * Note that {@code null} is used to represent defaults.
     */
    public MorphologyOptions() {
        //
    }

    /**
     * constructor for {@code MorphologyOptions}
     * @param disambiguate whether the linguistics analysis should disambiguate results
     * @param query request query processing
     * @param partOfSpeechTagSet the repetoire of part of speech tags.
     */
    public MorphologyOptions(
            Boolean disambiguate,
            Boolean query,
            PartOfSpeechTagSet partOfSpeechTagSet
    ) {
        this.disambiguate = disambiguate;
        this.query = query;
        this.partOfSpeechTagSet = partOfSpeechTagSet;
    }

    /**
     * get whether the linguistics analysis should disambiguate results
     * @return whether the linguistics analysis should disambiguate results
     */
    public Boolean getDisambiguate() {
        return disambiguate;
    }

    /**
     * DocumentRequest query processing. Linguistics analysis may change its behavior
     * to reflect the fact that query input is often not in full sentences;
     * Typically, this disables disambiguation.
     * @return request query processing
     */
    public Boolean getQuery() {
        return query;
    }

    /**
     * set whether the linguistics analysis should disambiguate results
     * @param disambiguate whether the linguistics analysis should disambiguate results
     */
    public void setDisambiguate(Boolean disambiguate) {
        this.disambiguate = disambiguate;
    }

    /**
     * DocumentRequest query processing. Linguistics analysis may change its behavior
     * to reflect the fact that query input is often not in full sentences;
     * typically, this disables disambiguation
     * @param query request query processing
     */
    public void setQuery(Boolean query) {
        this.query = query;
    }

    /**
     * @return the tag set used when returning part of speech tags. A {@code null} value
     * indicates the default.
     */
    public PartOfSpeechTagSet getPartOfSpeechTagSet() {
        return partOfSpeechTagSet;
    }

    /**
     * Set the tag set used to return part of speech tags. The default is {@link PartOfSpeechTagSet#upt16}.
     * @param partOfSpeechTagSet the tag set.
     */
    public void setPartOfSpeechTagSet(PartOfSpeechTagSet partOfSpeechTagSet) {
        this.partOfSpeechTagSet = partOfSpeechTagSet;
    }

    @Override
    public int hashCode() {
        int result = disambiguate != null ? disambiguate.hashCode() : 0;
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + (partOfSpeechTagSet != null ? partOfSpeechTagSet.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code MorphologyOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MorphologyOptions)) {
            return false;
        }

        MorphologyOptions that = (MorphologyOptions) o;
        return disambiguate != null ? disambiguate.equals(that.getDisambiguate()) : that.disambiguate == null
                && query != null ? query.equals(that.getQuery()) : that.query == null
                && partOfSpeechTagSet == that.partOfSpeechTagSet;
    }
}
