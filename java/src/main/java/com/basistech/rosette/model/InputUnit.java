package com.basistech.rosette.model;

public enum InputUnit {
    DOC("doc"),
    SENTENCE("sentence"),
    WORDS("words"),
    QUERY("query");

    private String label;

    InputUnit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toValue() {
        return label;
    }
}
