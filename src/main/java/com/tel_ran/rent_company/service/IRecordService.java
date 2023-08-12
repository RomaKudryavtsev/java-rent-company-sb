package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.record.RecordDto;
import com.tel_ran.rent_company.dto.record.RentCarDto;
import com.tel_ran.rent_company.dto.record.ReturnCarDto;

import java.util.List;

public interface IRecordService {
    RentCarDto rentCar(RentCarDto rentDto);

    RecordDto returnCar(ReturnCarDto returnDto);

    List<RecordDto> getRecords(String fromDate, String toDate, Integer from, Integer size);
}
