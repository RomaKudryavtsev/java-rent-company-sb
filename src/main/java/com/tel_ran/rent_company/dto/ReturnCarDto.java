package com.tel_ran.rent_company.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReturnCarDto {
    @NotBlank
    String regNumber;
    @Positive
    Long licenseId;
    @NotBlank
    String returnDate;
    @Min(value = 0)
    @Max(value = 100)
    Integer damages;
    @Min(value = 0)
    @Max(value = 100)
    Integer tankPercent;
}
