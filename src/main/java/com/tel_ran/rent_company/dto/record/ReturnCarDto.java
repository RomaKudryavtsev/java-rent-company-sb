package com.tel_ran.rent_company.dto.record;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
