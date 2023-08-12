package com.tel_ran.rent_company.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = "license_id"))
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "license_id")
    Long licenseId;
    @Column(name = "name")
    String name;
    @Column(name = "birth_year")
    Integer birthYear;
    @Column(name = "phone")
    String phone;
    @OneToMany(targetEntity = RentRecord.class,
            mappedBy = "driver",
            fetch = FetchType.LAZY)
    List<RentRecord> records;
}
