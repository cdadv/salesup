package com.salesup.local.services;

import com.salesup.local.domains.SalesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.salesup.local.repositories.SalesRecordRepository;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    public List<SalesRecord> queryAllSalesRecords() {
        return salesRecordRepository.findAll();
    }
}
