package com.tel_ran.rent_company.util;

import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.entity.RentRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordMapper {

    public static RentRecord rentCarDtoToEntity(RentCarDto rentCarDto, DateTimeFormatter formatter) {
        RentRecord rentRecord = new RentRecord();
        rentRecord.setRentDays(rentCarDto.getRentDays());
        rentRecord.setRentDate(LocalDate.parse(rentCarDto.getRentDate(), formatter));
        return rentRecord;
    }

    public static RentCarDto entityToRentCarDto(RentRecord rentRecord, DateTimeFormatter formatter) {
        return RentCarDto.builder()
                .recordId(rentRecord.getId())
                .regNumber(rentRecord.getCar().getRegNumber())
                .licenseId(rentRecord.getDriver().getLicenseId())
                .rentDate(rentRecord.getRentDate().format(formatter))
                .rentDays(rentRecord.getRentDays())
                .build();
    }
}
