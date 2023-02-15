package com.deloop.application.models.requests;

import com.deloop.domain.enums.PaymentMethod;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@Builder
public class ReportSalesRequest {

    @ApiParam(hidden = true)
//    private final String dateFormat = "dd-MM-yyyy HH:mm:ss"; // format seems to be MM-dd-yyyy HH:mm:ss
    private final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Builder.Default
    @ApiParam(example = "2022-04-26 10:11:10")  // format seems always to be MM-dd-yyyy HH:mm:ss
    @DateTimeFormat(pattern = dateFormat)
    private LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 26), LocalTime.MIDNIGHT);

    @Builder.Default
    @ApiParam(example = "2023-04-26 10:11:12")
    @DateTimeFormat(pattern = dateFormat)
    private LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2023, 4, 26), LocalTime.MIDNIGHT);

    @Builder.Default
    @ApiParam(example = "CASH")
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @Builder.Default
    @ApiParam(example = "delcoker@live.ca")
    private String email = "";

}
