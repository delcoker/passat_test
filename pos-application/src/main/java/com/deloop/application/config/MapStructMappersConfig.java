package com.deloop.application.config;

import com.deloop.infrastructure.mappers.SentimentMapper;
import com.deloop.infrastructure.mappers.SentimentMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructMappersConfig {

    @Bean
    SentimentMapper sentimentMapper() {
        return new SentimentMapperImpl();
    }

}
