package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepo extends JpaRepository<RentRecord, Long> {
}
