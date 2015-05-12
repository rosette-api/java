package com.basistech.rosette.model;

public class LanguageRequest extends Request {
    private LanguageOptions options;

    public LanguageRequest(
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            LanguageOptions options
    ) {
        super(null, content, contentUri, contentType, unit);
        this.options = options;
    }

    public LanguageRequest() {
    }

    public LanguageOptions getOptions() {
        return options;
    }
}

