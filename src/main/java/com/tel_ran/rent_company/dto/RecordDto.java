package com.tel_ran.rent_company.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordDto {
    Long recordId;
    String rentDate;
    String returnDate;
    Integer rentDays;
    Integer damages;
    Integer tankPercent;
    Double cost;
    String regNumber;
    Long licenseId;
}
