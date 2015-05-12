package com.basistech.rosette.model;

public class EntityRequest extends Request {
    private EntityOptions options;

    public EntityRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            EntityOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    public EntityRequest() {
    }

    public EntityOptions getOptions() {
        return options;
    }
}
