package com.basistech.rosette.model;

public class Lemma {
    private String text;
    private String lemma;

    public Lemma() {}

    public Lemma(
            String text,
            String lemma
    ) {
        this.text = text;
        this.lemma = lemma;
    }

    public String getText() {
        return text;
    }

    public String getLemma() {
        return lemma;
    }
}