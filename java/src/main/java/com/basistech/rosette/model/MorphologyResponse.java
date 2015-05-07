package com.basistech.rosette.model;

import java.util.List;

public final class MorphologyResponse extends Response {
    private List<PartOfSpeech> posTags;

    private List<Lemma> lemmas;

    private List<Decompounding> compounds;

    private List<HanReadings> hanReadings;

    public MorphologyResponse() {}

    public MorphologyResponse(
            String requestId,
            List<PartOfSpeech> posTags,
            List<Lemma> lemmas,
            List<Decompounding> compounds,
            List<HanReadings> hanReadings
    ) {
        super(requestId);
        this.posTags = posTags;
        this.lemmas = lemmas;
        this.compounds = compounds;
        this.hanReadings = hanReadings;
    }

    public List<PartOfSpeech> getPosTags() {
        return posTags;
    }

    public List<Lemma> getLemmas() {
        return lemmas;
    }

    public List<Decompounding> getCompounds() {
        return compounds;
    }

    public List<HanReadings> getHanReadings() {
        return hanReadings;
    }
}
