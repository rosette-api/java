package com.basistech.rosette.model;

public class SentimentRequest extends Request {
    private SentimentOptions options;

    public SentimentRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            SentimentOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    public SentimentRequest() {
    }

    public SentimentOptions getOptions() {
        return options;
    }
}
