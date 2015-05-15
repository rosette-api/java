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
     * list of part of speech tags 
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
}
