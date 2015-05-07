package com.basistech.rosette.model;

public class Name {

    private String text;
    private String entityType;
    private String script;
    private String language;

    public Name() {}

    public Name(String name,
                String entityType,
                String script,
                String language) {
        this.text = name;
        this.entityType = entityType;
        this.script = script;
        this.language = language;
    }

    public Name(String name) {
        this.text = name;
        this.entityType = "PERSON";
        this.script = ISO15924.Zyyy;
        this.language = LanguageCode.UNKNOWN;
    }

    public String getText() {
        return text;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getScript() {
        return script;
    }

    public String getLanguage() {
        return language;
    }
}
