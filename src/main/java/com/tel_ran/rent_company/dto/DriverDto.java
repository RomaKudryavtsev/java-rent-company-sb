package com.tel_ran.rent_company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverDto {
    Long driverId;
    @Positive
    Long licenseId;
    @NotBlank
    String name;
    @Positive
    Integer birthYear;
    @NotBlank
    String phone;

}
