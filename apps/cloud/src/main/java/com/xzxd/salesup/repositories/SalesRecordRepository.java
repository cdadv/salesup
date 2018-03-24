package com.xzxd.salesup.repositories;

import com.xzxd.salesup.domains.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRecordRepository extends JpaRepository<SalesRecord, Long> {
}
