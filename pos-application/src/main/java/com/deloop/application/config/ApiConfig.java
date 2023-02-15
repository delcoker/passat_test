package com.deloop.application.config;

import com.deloop.application.services.SentimentService;
import com.deloop.application.services.SentimentServiceImpl;
import com.deloop.domain.config.DomainConfig;
import com.deloop.domain.repositories.SentimentRepository;
import com.deloop.infrastructure.config.DBUserRepositoryConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({EmailConfig.class, SpringFoxConfig.class,
        WebSecurityConfig.class, DBUserRepositoryConfiguration.class,
        /*DBRepositoryConfiguration.class, */DomainConfig.class})
public class ApiConfig {

//    @Bean
//    ReportService reportService(SentimentRepository sentimentRepository, ReportRepository reportRepository, AuthenticationFacade authenticationFacade) {
//        return new ReportServiceImpl(sentimentRepository, reportRepository, authenticationFacade);
//    }

    @Bean
    SentimentService sentimentService(SentimentRepository sentimentRepository) {
        return new SentimentServiceImpl(sentimentRepository);
    }

}
