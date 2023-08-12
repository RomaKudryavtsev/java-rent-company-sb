package com.tel_ran.rent_company.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentCarDto {
    Long recordId;
    String regNumber;
    Long licenseId;
    String rentDate;
    Integer rentDays;
}
