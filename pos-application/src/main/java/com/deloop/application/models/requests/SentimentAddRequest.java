package com.deloop.application.models.requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentimentAddRequest {

    @Builder.Default
    private String reportName = "";

    @Builder.Default
    private String reportText = "";

}
