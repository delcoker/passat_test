package com.deloop.application.services;

import com.deloop.application.models.requests.SentimentAddRequest;
import com.deloop.domain.models.ReportDo;
import com.deloop.domain.models.SentimentDo;
import com.deloop.domain.repositories.SentimentRepository;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SentimentServiceImpl implements SentimentService {
    private final SentimentRepository sentimentRepository;

    @Override // UserDao should be FE object later
    public List<SentimentDo> addSentiments(SentimentAddRequest sentimentAddRequest) {

        List<String> reportSentences = List.of(sentimentAddRequest.getReportText().split("\\."));

        List<SentimentDo> sentimentDos = reportSentences.stream()
                .map(reportSentence -> getScore(reportSentence, sentimentAddRequest.getReportName()))
                .toList();

        return sentimentRepository.save(sentimentDos);

    }

    @Override
    public List<SentimentDo> getSentiments(SentimentAddRequest sentimentAddRequest) {

        List<SentimentDo> sentimentDos = sentimentRepository.findAll(sentimentAddRequest.getReportName());



        return sentimentDos;
    }

    private SentimentDo getScore(String sentence, String reportName) {

        SentimentPolarities sentimentAnalyzer = SentimentAnalyzer.getScoresFor(sentence);

        double score = sentimentAnalyzer.getCompoundPolarity();
        String sentiment = "NEUTRAL";

        if (score >= 0.05) {
            sentiment = "POSITIVE";
        } else if (score <= -0.05) {
            sentiment = "NEGATIVE";
        }

        return SentimentDo.builder()
                .sentiment(sentiment)
                .score(score)
                .sentence(sentence)
                .reportDo(ReportDo.builder().name(reportName).build())
                .build();
    }

}