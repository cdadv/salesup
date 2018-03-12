package com.XZXD.salesup.controllers;

import com.XZXD.salesup.domains.SalesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.XZXD.salesup.services.QueryService;

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
