package com.tel_ran.rent_company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
