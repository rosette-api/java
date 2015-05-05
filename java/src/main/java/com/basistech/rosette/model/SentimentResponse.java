package com.basistech.rosette.model;

import java.util.List;

/**
 *  Simple api response data model for sentiment analysis
 **/
public final class SentimentResponse extends Response {

    private List<Sentiment> sentiment;

    public SentimentResponse() {}

    public SentimentResponse(String requestId,
                             List<Sentiment> sentiment) {
        super(requestId);
        this.sentiment = sentiment;
    }

    public List<Sentiment> getSentiment() {
        return sentiment;
    }
}

