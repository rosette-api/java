package com.basistech.rosette.model;

public enum ProcessorType {
    /** The statistical entity extractor. */
    statistical,
    /** The gazetteer based entity extractor. */
    acceptGazetteer,
    /** The regular expression based entity extractor. */
    acceptRegex,
    /** The gazetteer based entity rejector. */
    rejectGazetteer,
    /** The regular expression based entity rejector. */
    rejectRegex,
    /** The entity redactor, which disambiguates overlapping entities. */
    redactor,
    /** The entity joiner, which joins adjacent entities. */
    joiner
}
