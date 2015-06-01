package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.Decompounding;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.Lemma;
import com.basistech.rosette.apimodel.PartOfSpeech;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MorphologyResponseMixin {
    @JsonCreator
    public MorphologyResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("posTags") List<PartOfSpeech> posTags,
            @JsonProperty("lemmas") List<Lemma> lemmas,
            @JsonProperty("compounds") List<Decompounding> compounds,
            @JsonProperty("han-readings") List<HanReadings> hanReadings
    ) {
        //
    }

}
