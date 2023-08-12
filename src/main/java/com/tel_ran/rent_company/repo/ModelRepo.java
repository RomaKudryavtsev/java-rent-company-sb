package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepo extends JpaRepository<Model, Long> {
    boolean existsByModelName(String modelName);
    Model findByModelName(String modelName);
}
