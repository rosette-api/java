package com.basistech.rosette.model;

public class LinkedEntityRequest extends Request {
    private LinkedEntityOption options;

    public LinkedEntityRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            LinkedEntityOption options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    public LinkedEntityRequest() {
    }

    public LinkedEntityOption getOptions() {
        return options;
    }
}
