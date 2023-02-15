package com.deloop.domain.services;


import com.deloop.domain.enums.PaymentMethod;
import com.deloop.domain.models.ReportDo;

import java.time.LocalDateTime;


public interface ReportService {

    ReportDo getReport(LocalDateTime startDate, LocalDateTime endDate, PaymentMethod paymentMethod, String email);

}
