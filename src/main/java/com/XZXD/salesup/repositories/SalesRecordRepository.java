package com.XZXD.salesup.repositories;

import com.XZXD.salesup.domains.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRecordRepository extends JpaRepository<SalesRecord, Long> {
}
