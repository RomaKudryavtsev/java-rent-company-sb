package com.tel_ran.rent_company.dto.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatistGetModelsDto {
    @NotBlank
    String fromDate;
    @NotBlank
    String toDate;
    @Positive
    Integer fromAge;
    @Positive
    Integer toAge;
}
