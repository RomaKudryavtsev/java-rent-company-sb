package com.tel_ran.rent_company.dto.car;

import com.tel_ran.rent_company.dto.record.RecordDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveCarDto {
    CarResponseDto carResponseDto;
    List<RecordDto> records;
}
