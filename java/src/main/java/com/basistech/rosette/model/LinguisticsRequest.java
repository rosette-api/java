package com.basistech.rosette.model;

public class LinguisticsRequest extends Request {
    private LinguisticsOptions options;

    public LinguisticsRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            LinguisticsOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    public LinguisticsRequest() {
    }

    public LinguisticsOptions getOptions() {
        return options;
    }
}
