package com.salesup.cloud.loaders;

import com.salesup.cloud.domains.SalesRecord;
import com.salesup.cloud.repositories.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
public class DemoData implements ApplicationRunner{
    @Autowired
    private SalesRecordRepository salesRecordRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SalesRecord demoData1 = new SalesRecord();
        demoData1.setPrice(new BigDecimal(12.99));
        demoData1.setTime(new Timestamp(System.currentTimeMillis()));
        demoData1.setProductName("ding0");

        SalesRecord demoData2 = new SalesRecord();
        demoData2.setPrice(new BigDecimal(13.99));
        demoData2.setTime(new Timestamp(System.currentTimeMillis()));
        demoData2.setProductName("ding1");

        salesRecordRepository.save(demoData1);
        salesRecordRepository.save(demoData2);
    }
}
