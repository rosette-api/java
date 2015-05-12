package com.basistech.rosette.model;

public class LanguageWeight {
    private String language;
    private ISO15924 script;
    private Integer weight;

    public LanguageWeight() {
    }

    public LanguageWeight(
            String language,
            ISO15924 script,
            Integer weight
    ) {
        this.language = language;
        this.weight = weight;
        this.script = script;
    }

    public LanguageWeight(
            String language,
            Integer weight
    ) {
        this.language = language;
        this.weight = weight;
        this.script = null;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getWeight() {
        return weight;
    }

    public ISO15924 getScript() {
        return script;
    }
}
