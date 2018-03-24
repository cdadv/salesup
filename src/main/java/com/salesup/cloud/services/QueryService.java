package com.salesup.cloud.services;

import com.salesup.cloud.domains.SalesRecord;
import com.salesup.cloud.repositories.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    public List<SalesRecord> queryAllSalesRecords() {
        return salesRecordRepository.findAll();
    }
}
