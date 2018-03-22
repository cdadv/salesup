package com.salesup.cloud.repositories;

import com.salesup.cloud.domains.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRecordRepository extends JpaRepository<SalesRecord, Long> {
}
