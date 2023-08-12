package com.tel_ran.rent_company.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "records", schema = "public")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "rent_date")
    LocalDate rentDate;
    @Column(name = "return_date")
    LocalDate returnDate;
    @Column(name = "rent_days")
    Integer rentDays;
    @Column(name = "damages")
    Integer damages;
    @Column(name = "tank_percent")
    Integer tankPercent;
    @Column(name = "cost")
    Double cost;
    @ManyToOne(fetch = FetchType.LAZY)
    Driver driver;
    @ManyToOne(fetch = FetchType.LAZY)
    Car car;
}
