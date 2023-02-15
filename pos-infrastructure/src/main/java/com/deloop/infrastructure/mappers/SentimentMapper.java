package com.deloop.infrastructure.mappers;

import com.deloop.domain.models.SentimentDo;
import com.deloop.infrastructure.models.Sentiment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SentimentMapper {

    @Mapping(target = "reportDo", source = "report")
    SentimentDo map(Sentiment sentiment);

    @Mapping(target = "report", source = "reportDo")
    Sentiment map(SentimentDo domainSentiment);
}
