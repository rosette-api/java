package com.basistech.rosette.model;

public class Request {
    private String language;
    private String contentUri;
    private String content;
    private String contentType;
    private String unit;

    public Request() {}

    public Request(String language, String content, String contentUri, String contentType, InputUnit unit) {
        this.contentUri = contentUri;
        this.content = content;
        this.language = language;
        this.contentType = contentType;
        if (unit == null)
            this.unit = InputUnit.DOC.toValue();
        else
            this.unit = unit.toValue();
    }

    public String getContent() {
        return content;
    }

    public String getLanguage() {
        return language;
    }

    public String getUnit() {
        return unit;
    }

    public String getContentUri() {
        return contentUri;
    }

    public String getContentType() {
        return contentType;
    }
}
