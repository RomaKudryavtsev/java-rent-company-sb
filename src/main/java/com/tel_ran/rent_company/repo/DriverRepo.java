package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    Driver findByLicenseId(Long licenseId);

    boolean existsByLicenseId(Long licenseId);

    @Query(value = "select * from drivers as d " +
            "join records as r on d.id = r.driver_id " +
            "join cars as c on c.id = r.car_id " +
            "where c.reg_number = :regNumber", nativeQuery = true)
    List<Driver> findDriversByCar(String regNumber);

    @Query(value = "select * from drivers as d " +
            "join (select r.license_id, count(*) as count from records as r " +
            "group by r.license_id) count_records_for_license_id as counter on d.license_id = counter.license_id " +
            "where count = max(count)", nativeQuery = true)
    List<Driver> findMostActiveDrivers();
}
