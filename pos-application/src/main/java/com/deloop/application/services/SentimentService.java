package com.deloop.application.services;

import com.deloop.application.models.requests.SentimentAddRequest;
import com.deloop.domain.models.SentimentDo;

import java.util.List;

public interface SentimentService {

    List<SentimentDo> addSentiments(SentimentAddRequest sentimentAddRequest);  // UserDao should be FE object later


    List<SentimentDo> getSentiments(SentimentAddRequest sentimentAddRequest);// UserDao should be FE object later


}