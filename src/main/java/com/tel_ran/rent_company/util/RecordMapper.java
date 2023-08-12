package com.tel_ran.rent_company.util;

import com.tel_ran.rent_company.dto.record.RecordDto;
import com.tel_ran.rent_company.dto.record.RentCarDto;
import com.tel_ran.rent_company.entity.RentRecord;

import java.time.format.DateTimeFormatter;

public class RecordMapper {

    public static RentRecord rentCarDtoToEntity(RentCarDto rentCarDto, DateTimeFormatter formatter) {
        RentRecord rentRecord = new RentRecord();
        rentRecord.setRentDays(rentCarDto.getRentDays());
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

    public static RecordDto entityToRecordDto(RentRecord rentRecord, DateTimeFormatter formatter) {
        return RecordDto.builder()
                .recordId(rentRecord.getId())
                .rentDate(rentRecord.getRentDate() == null ? "" : rentRecord.getRentDate().format(formatter))
                .returnDate(rentRecord.getReturnDate() == null ? "" : rentRecord.getReturnDate().format(formatter))
                .rentDays(rentRecord.getRentDays())
                .damages(rentRecord.getDamages() == null ? -1 : rentRecord.getDamages())
                .tankPercent(rentRecord.getTankPercent() == null ? -1 : rentRecord.getDamages())
                .cost(rentRecord.getCost() == null ? -1 : rentRecord.getCost())
                .regNumber(rentRecord.getCar().getRegNumber())
                .licenseId(rentRecord.getDriver().getLicenseId())
                .build();
    }
}
