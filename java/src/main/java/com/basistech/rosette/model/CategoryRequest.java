package com.basistech.rosette.model;

public class CategoryRequest extends Request {
    private CategoryOptions options;

    public CategoryRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            CategoryOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    public CategoryRequest() {
    }

    public CategoryOptions getOptions() {
        return options;
    }
}
