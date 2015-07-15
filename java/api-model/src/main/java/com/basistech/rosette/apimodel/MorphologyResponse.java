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
 * Simple API response data model returned by MorphologyRequest
 *
 * See {@code LinguisticRequest} and {@code MorphologicalFeature} for details.
 */
public final class MorphologyResponse extends Response {

    private final List<PartOfSpeech> posTags;
    private final List<Lemma> lemmas;
    private final List<CompoundComponents> compounds;
    private final List<HanReadings> hanReadings;
    
    /**
     * constructor for {@code MorphologyResponse}
     * @param requestId request id
     * @param posTags list of part of speech tags
     * @param lemmas list of lemmas
     * @param compounds list of compounds
     * @param hanReadings list of Han readings
     */
    public MorphologyResponse(
            String requestId,
            List<PartOfSpeech> posTags,
            List<Lemma> lemmas,
            List<CompoundComponents> compounds,
            List<HanReadings> hanReadings) {
        super(requestId);
        this.posTags = posTags;
        this.lemmas = lemmas;
        this.compounds = compounds;
        this.hanReadings = hanReadings;
    }

    /**
     * get the list of part of speech tags 
     * @return the list of part of speech tags
     */
    public List<PartOfSpeech> getPosTags() {
        return posTags;
    }

    /**
     * get the list of lemmas 
     * @return the list of lemmas
     */
    public List<Lemma> getLemmas() {
        return lemmas;
    }

    /**
     * get the list of compounds 
     * @return the list of compounds
     */
    public List<CompoundComponents> getCompounds() {
        return compounds;
    }

    /**
     * get the list of Han readings
     * @return the list of Han readings
     */
    public List<HanReadings> getHanReadings() {
        return hanReadings;
    }

    @Override
    public int hashCode() {
        int result = posTags != null ? posTags.hashCode() : 0;
        result = 31 * result + (lemmas != null ? lemmas.hashCode() : 0);
        result = 31 * result + (compounds != null ? compounds.hashCode() : 0);
        result = 31 * result + (hanReadings != null ? hanReadings.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code MorphologyResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MorphologyResponse)) {
            return false;
        }

        MorphologyResponse that = (MorphologyResponse) o;
        return super.equals(o)
                && posTags != null ? posTags.equals(that.getPosTags()) : that.posTags == null
                && lemmas != null ? lemmas.equals(that.getLemmas()) : that.lemmas == null
                && compounds != null ? compounds.equals(that.getCompounds()) : that.compounds == null
                && hanReadings != null ? hanReadings.equals(that.getHanReadings()) : that.hanReadings == null;
    }
}
