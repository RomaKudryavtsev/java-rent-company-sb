package com.tel_ran.rent_company.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "models", schema = "public")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long modelId;
    @Column(name = "model_name")
    String modelName;
    @Column(name = "gas_tank")
    Integer gasTank;
    @Column(name = "company")
    String company;
    @Column(name = "country")
    String country;
    @Column(name = "day_price")
    Integer dayPrice;
    @OneToMany(targetEntity = Car.class,
            mappedBy = "model",
            fetch = FetchType.LAZY)
    List<Car> cars;
}
