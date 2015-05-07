package com.basistech.rosette.model;

public class PartOfSpeech {
    private String text;
    private String pos;

    public PartOfSpeech() {}

    public PartOfSpeech(String text, String pos) {
        this.text = text;
        this.pos = pos;
    }

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }
}
