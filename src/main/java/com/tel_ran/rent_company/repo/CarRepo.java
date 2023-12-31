package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    boolean existsByRegNumber(String regNumber);

    Page<Car> findByModel_ModelName(String modelName, Pageable pageRequest);

    Car findByRegNumber(String regNumber);

    @Query(value = "select * from cars as c " +
            "join records as r on c.id = r.car_id " +
            "join drivers as d on d.id = r.driver_id " +
            "where d.license_id = :licenseId", nativeQuery = true)
    Page<Car> findCarsByDriver(Long licenseId, Pageable pageRequest);

    List<Car> findByModel_ModelName(String modelName);
}
