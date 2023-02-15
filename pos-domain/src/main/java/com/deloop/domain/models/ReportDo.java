package com.deloop.domain.models;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportDo {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long id;

    private String name;

    private String details;
}
