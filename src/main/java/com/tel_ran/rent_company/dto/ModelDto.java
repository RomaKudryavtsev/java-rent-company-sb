package com.tel_ran.rent_company.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelDto {
    Long modelId;
    String modelName;
    Integer gasTank;
    String company;
    String country;
    Integer dayPrice;
}
