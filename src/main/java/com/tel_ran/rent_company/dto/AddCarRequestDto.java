package com.tel_ran.rent_company.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCarRequestDto {
    @NotBlank
    String regNumber;
    @NotBlank
    String color;
    @NotBlank
    String modelName;
}
