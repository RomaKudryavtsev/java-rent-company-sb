package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.RecordDto;
import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.dto.ReturnCarDto;
import com.tel_ran.rent_company.service.IRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements IRecordService {
    @Override
    public RentCarDto rentCar(RentCarDto rentDto) {
        return null;
    }

    @Override
    public ReturnCarDto returnCar(ReturnCarDto returnDto) {
        return null;
    }

    @Override
    public List<RecordDto> getRecords(String fromDate, String toDate) {
        return null;
    }
}
