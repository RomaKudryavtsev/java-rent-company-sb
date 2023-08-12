package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    boolean existsByRegNumber(String regNumber);

    List<Car> findByModel_ModelName(String modelName);

    Car findByRegNumber(String regNumber);

    @Query(value = "select * from cars as c " +
            "join records as r on c.id = r.id " +
            "join drivers as d on r.id = d.id " +
            "where d.license_id = :licenseId", nativeQuery = true)
    List<Car> findCarsByDriver(Long licenseId);

}
