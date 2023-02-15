package com.deloop.infrastructure.config;


import com.deloop.domain.repositories.SentimentRepository;
import com.deloop.infrastructure.DBEbeanService;
import com.deloop.infrastructure.mappers.SentimentMapper;
import com.deloop.infrastructure.repositories.SentimentRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Import({DBConfiguration.class})
public class DBUserRepositoryConfiguration {

    @Bean
    SentimentRepository sentimentRepository(DBEbeanService dbEbeanService, SentimentMapper sentimentMapper/*,  PasswordEncoder passwordEncoder*/) {
        return new SentimentRepositoryImpl(dbEbeanService.getDb(), sentimentMapper /*,  passwordEncoder*/);
    }

}
