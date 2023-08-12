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
            "join records as r on d.id = r.id " +
            "join cars as c on c.id = r.id " +
            "where c.reg_number = :regNumber", nativeQuery = true)
    List<Driver> findDriversByCar(String regNumber);
}
