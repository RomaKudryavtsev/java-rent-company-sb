package com.tel_ran.rent_company.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CarResponseDto {
    Long carId;
    String regNumber;
    String color;
    String state;
    Boolean inUse;
    Boolean toBeRemoved;
    String modelName;
}
