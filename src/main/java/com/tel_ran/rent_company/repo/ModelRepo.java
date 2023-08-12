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

    @Query(value = "select m.* from models as m " +
            "join cars as c on m.id = c.model_id " +
            "join records as r on c.id = r.car_id " +
            "join drivers as d on d.id = r.driver_id " +
            "join (select r.car_id, count(*) as count from records as r " +
            "group by r.car_id) counter on r.car_id = counter.car_id " +
            "where (cast(r.rent_date as date)) between :from and :to and " +
            "d.birth_year < :fromYear and d.birth_year > :toYear " +
            "group by m.id " +
            "having count(*) = (select max(count) from " +
            "(select count(*) as count from records as r " +
            "group by r.car_id) sub)", nativeQuery = true)
    List<Model> findMostPopularModels(LocalDate from, LocalDate to, int fromYear, int toYear);

    @Query(value = "select m.* from models as m " +
            "join cars as c on m.id = c.model_id " +
            "join records as r on c.id = r.car_id " +
            "join (select r.car_id, sum(r.cost) as sum from records as r " +
            "group by r.car_id) counter on c.id = counter.car_id " +
            "where (cast(r.rent_date as date)) between :from and :to " +
            "group by m.id, counter.sum " +
            "having counter.sum = (select max(max_sum) from " +
            "(select sum(r.cost) as max_sum from records as r " +
            "group by r.car_id) sub)", nativeQuery = true)
    List<Model> findMostProfitableModels(LocalDate from, LocalDate to);
}
