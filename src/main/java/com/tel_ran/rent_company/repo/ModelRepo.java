package com.tel_ran.rent_company.repo;

import com.tel_ran.rent_company.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<Model, Long> {
    boolean existsByModelName(String modelName);

    Model findByModelName(String modelName);

    @Query(value = "select * from models as m " +
            "join cars as c on m.id = c.model_id " +
            "join records as r on c.id = r.car_id " +
            "join drivers as d on d.id = r.driver_id " +
            "join (select r.reg_number, count(*) as count from records as r " +
            "group by r.reg_number) count_records_for_reg_number as counter on r.reg_number = counter.reg_number " +
            "group by c.model_id " +
            "having (case(r.rent_date as date)) between :from and :to and " +
            "d.birth_year > :fromYear and d.birth_year < :toYear and " +
            "count = max(count)", nativeQuery = true)
    List<Model> findMostPopularModels(LocalDate from, LocalDate to, int fromYear, int toYear);

    @Query(value = "select * from models as m " +
            "join cars as c on m.id = c.model_id " +
            "join records as r on c.id = r.car_id " +
            "join drivers as d on d.id = r.driver_id " +
            "join (select r.reg_number, sum(cost) as sum from records as r " +
            "group by r.reg_number) count_cost as counter on r.reg_number = counter.reg_number " +
            "group by c.model_id " +
            "having (case(r.rent_date as date)) between :from and :to and " +
            "sum = max(sum)", nativeQuery = true)
    List<Model> findMostProfitableModels(LocalDate from, LocalDate to);
}
