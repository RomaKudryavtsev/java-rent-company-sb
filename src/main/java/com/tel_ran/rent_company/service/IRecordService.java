package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.RecordDto;
import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.dto.ReturnCarDto;

import java.util.List;

public interface IRecordService {
    RentCarDto rentCar(RentCarDto rentDto);

    ReturnCarDto returnCar(ReturnCarDto returnDto);

    List<RecordDto> getRecords(String fromDate, String toDate);
}
