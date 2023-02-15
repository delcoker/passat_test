package com.deloop.domain.repositories;

import com.deloop.domain.exceptions.EntityNotFoundException;
import com.deloop.domain.models.SentimentDo;

import java.util.List;
import java.util.Optional;

public interface SentimentRepository {

    List<SentimentDo> findAll(String reportName);

    Optional<SentimentDo> findByEmail(String email);

    Optional<SentimentDo> find(Long id) throws Exception;

    SentimentDo save(SentimentDo domain) ;

    List<SentimentDo> save(List<SentimentDo> domain) ;

    SentimentDo update(SentimentDo domain) throws EntityNotFoundException;

    boolean delete(SentimentDo domain);

    boolean resetPassword(String email);

    boolean changeMyPassword(String principalEmail, String newPassword);
}
