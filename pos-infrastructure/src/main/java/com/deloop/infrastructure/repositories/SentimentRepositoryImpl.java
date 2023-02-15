package com.deloop.infrastructure.repositories;

import com.deloop.domain.exceptions.EntityNotFoundException;
import com.deloop.domain.models.SentimentDo;
import com.deloop.domain.repositories.SentimentRepository;
import com.deloop.infrastructure.mappers.SentimentMapper;
import com.deloop.infrastructure.models.Report;
import com.deloop.infrastructure.models.Sentiment;
import com.deloop.infrastructure.models.query.QSentiment;
import io.ebean.Database;
import io.ebean.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SentimentRepositoryImpl implements SentimentRepository {
    private final Database db;
    private final SentimentMapper sentimentMapper;

    @Override
    public List<SentimentDo> findAll(String reportName) {
        if (reportName == null || StringUtils.isBlank(reportName)) {

            List<Sentiment> sentiments = new QSentiment(db)
                    .findList();

            return new QSentiment(db)
                    .score.desc()
                    .findList().stream()
                    .map(sentimentMapper::map)
                    .toList();
        }

        return new QSentiment(db)
                .report.name.eqIfPresent(reportName)
                .findList().stream()
                .sorted(Comparator.comparing(Sentiment::getScore))
                .map(sentimentMapper::map)
                .toList();
    }

    @Override
//    @Cacheable("Employee")
    public Optional<SentimentDo> findByEmail(String email) {


        return null;
    }

    @Override
    public Optional<SentimentDo> find(Long id) throws EntityNotFoundException {

        return null;
    }

    @Override
    public SentimentDo save(SentimentDo domain) {
        // check if item with same name exists
        Sentiment sentiment = sentimentMapper.map(domain);

        Report report = Report.builder()
                .name(domain.getReportDo().getName())
                .sentiments(Collections.singletonList(sentiment))
                .build();

        try {
            db.save(report);
        } catch (DuplicateKeyException duplicateKeyException) {
//            ItemCategory itemCategory = new QItemCategory(db).id.eq(item.getItemCategory().getId()).findOne();
//            String message = String.format("Item with this name %s already exists in category: %s",
//                    item.getName(), itemCategory.getName());

            throw new DuplicateKeyException("message", duplicateKeyException);
        }
        return sentimentMapper.map(sentiment);
    }

    @Override
    public List<SentimentDo> save(List<SentimentDo> domain) {
        List<Sentiment> sentiments = domain.stream().map(sentimentMapper::map).toList();

        Report report = Report.builder()
                .name(domain.get(0).getReportDo().getName())
                .sentiments(sentiments)
                .build();


        db.save(report);
        return domain;
    }

    @Override
    public SentimentDo update(SentimentDo domain) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean delete(SentimentDo employee) {
        return false;
    }

    @Override
    public boolean resetPassword(String email) {
        return false;
    }

    @Override
    public boolean changeMyPassword(String principalEmail, String password) {
        return false;
    }
}
