package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepo extends JpaRepository<RentRecord, Long> {
    @Query(value = "select * from records as r " +
            "where (cast(r.rent_date as date)) between :from and :to",
            nativeQuery = true)
    List<RentRecord> findAllRecordsBetweenRentDates(LocalDate from, LocalDate to);

    RentRecord findByCar_RegNumberAndDriver_LicenseId(String regNumber, Long licenseId);

    List<RentRecord> findAllByCar_RegNumber(String regNumber);
}
