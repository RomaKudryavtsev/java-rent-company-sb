package com.tel_ran.rent_company.dto.driver;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
