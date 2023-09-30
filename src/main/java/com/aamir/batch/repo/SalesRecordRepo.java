package com.aamir.batch.repo;

import com.aamir.batch.entity.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRecordRepo extends JpaRepository<SalesRecord, Long> {
}
