package com.tel_ran.rent_company.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = "reg_number"))
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "reg_number")
    String regNumber;
    @Column(name = "color")
    String color;
    @Enumerated(EnumType.STRING)
    State state;
    @Column(name = "in_use")
    Boolean inUse;
    @Column(name = "to_be_removed")
    Boolean toBeRemoved;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    Model model;
    @OneToMany(targetEntity = RentRecord.class,
            mappedBy = "car",
            fetch = FetchType.LAZY)
    List<RentRecord> records;
}
