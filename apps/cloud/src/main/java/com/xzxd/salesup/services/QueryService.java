package com.xzxd.salesup.services;

import com.xzxd.salesup.domains.SalesRecord;
import com.xzxd.salesup.repositories.SalesRecordRepository;
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
