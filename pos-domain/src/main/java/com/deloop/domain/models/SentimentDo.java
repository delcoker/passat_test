package com.deloop.domain.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class SentimentDo implements Serializable {

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    private long id;

    private String sentence;

    private String sentiment;

    private double score;

    private ReportDo reportDo;
}
