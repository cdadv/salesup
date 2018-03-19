package com.XZXD.salesup.services;

import com.XZXD.salesup.domains.SalesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.XZXD.salesup.repositories.SalesRecordRepository;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    public List<SalesRecord> queryAllSalesRecord() {
        return salesRecordRepository.findAll();
    }
}
