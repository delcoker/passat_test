package com.deloop.infrastructure.repositories;

import com.deloop.domain.models.ReportDo;
import com.deloop.domain.repositories.ReportRepository;
import io.ebean.Database;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final Database db;
//    private final ReportMapper reportMapper;


    @Override
    public ReportDo add(ReportDo reportDo) {
        return null;
    }
}
