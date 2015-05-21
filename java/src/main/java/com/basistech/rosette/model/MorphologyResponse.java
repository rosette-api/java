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

import java.util.List;

/**
 * Simple api response data model for morphology requests 
 */
public final class MorphologyResponse extends Response {
    private List<PartOfSpeech> posTags;
    private List<Lemma> lemmas;
    private List<Decompounding> compounds;
    private List<HanReadings> hanReadings;
    
    public MorphologyResponse() { super(); }

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
            List<Decompounding> compounds,
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
    public List<Decompounding> getCompounds() {
        return compounds;
    }

    /**
     * get the list of Han readings
     * @return the list of Han readings
     */
    public List<HanReadings> getHanReadings() {
        return hanReadings;
    }

    /**
     * set the list of part of speech tags
     * @param posTags the list of part of speech tags
     */
    public void setPosTags(List<PartOfSpeech> posTags) {
        this.posTags = posTags;
    }

    /**
     * set the list of lemmas 
     * @param lemmas the list of lemmas
     */
    public void setLemmas(List<Lemma> lemmas) {
        this.lemmas = lemmas;
    }

    /**
     * set the list of compounds 
     * @param compounds the list of compounds
     */
    public void setCompounds(List<Decompounding> compounds) {
        this.compounds = compounds;
    }

    /**
     * set the list of Han readings
     * @param hanReadings the list of Han readings
     */
    public void setHanReadings(List<HanReadings> hanReadings) {
        this.hanReadings = hanReadings;
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
        if (o instanceof MorphologyResponse) {
            MorphologyResponse that = (MorphologyResponse) o;
            return super.equals(o)
                    && posTags != null ? posTags.equals(that.getPosTags()) : posTags == that.getPosTags()
                    && lemmas != null ? lemmas.equals(that.getLemmas()) : lemmas == that.getLemmas()
                    && compounds != null ? compounds.equals(that.getCompounds()) : compounds == that.getCompounds()
                    && hanReadings != null ? hanReadings.equals(that.getHanReadings()) : hanReadings == that.getHanReadings();
        } else {
            return false;
        }
    }
}
