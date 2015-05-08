package com.basistech.rosette;

public class RosetteAPIRequest {
    private String contentUri;
    private String content;
    private String language;
    private String unit;

    public RosetteAPIRequest() {}

    public RosetteAPIRequest(String contentUri, String content, String language, String unit) {
        this.contentUri = contentUri;
        this.content = content;
        this.language = language;
        this.unit = unit;
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
}
