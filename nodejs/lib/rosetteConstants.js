"use strict";

var dataFormat = {
    "SIMPLE": "",
    "JSON": "application/json",
    "HTML": "text/html",
    "XHTML": "application/xhtml+xml",
    "UNSPECIFIED": "application/octet-stream"
};

var inputUnit = {
    "DOC": "doc",
    "SENTENCE": "sentence"
};

var morphologyOutput = {
    "LEMMAS": "lemmas",
    "PARTS_OF_SPEECH": "parts-of-speech",
    "COMPOUND_COMPONENTS": "compound-components",
    "HAN_READINGS": "han-readings",
    "COMPLETE": "complete"
};

exports.dataFormat = dataFormat;
exports.inputUnit = inputUnit;
exports.morpholoyOutput = morphologyOutput;
