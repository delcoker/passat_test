package com.deloop.domain.services;


import com.deloop.domain.enums.PaymentMethod;
import com.deloop.domain.models.ReportDo;
import com.deloop.domain.repositories.ReportRepository;
import com.deloop.domain.repositories.SentimentRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SentimentRepository sentimentRepository;
    private final ReportRepository reportRepository;
    private final AuthenticationFacade authenticationFacade;

    public ReportDo getReport(LocalDateTime startDate, LocalDateTime endDate, PaymentMethod paymentMethod, String email) {


        return null;
    }

}
