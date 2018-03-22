package com.salesup.cloud.controllers;

import com.salesup.cloud.domains.SalesRecord;
import com.salesup.cloud.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class QueryController {

    @Autowired
    QueryService queryService;

    @RequestMapping("/query/all/sales_record")
    public List<SalesRecord> queryAllSales() {
        return queryService.queryAllSalesRecords();
    }

}
