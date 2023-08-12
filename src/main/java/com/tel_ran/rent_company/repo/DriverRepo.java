package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    Driver findByLicenseId(Long licenseId);

    boolean existsByLicenseId(Long licenseId);

    @Query(value = "select * from drivers as d " +
            "join records as r on d.id = r.driver_id " +
            "join cars as c on c.id = r.car_id " +
            "where c.reg_number = :regNumber", nativeQuery = true)
    Page<Driver> findDriversByCar(String regNumber, Pageable pageRequest);

    @Query(value = "select * from drivers as d " +
            "join (select r.driver_id, count(*) as count from records as r " +
            "group by r.driver_id) counter on d.id = counter.driver_id " +
            "where counter.count = (select max(count) from " +
            "(select count(*) as count from records group by driver_id) sub)", nativeQuery = true)
    Page<Driver> findMostActiveDrivers(Pageable pageRequest);
}
