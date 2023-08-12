package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.RecordDto;
import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.dto.ReturnCarDto;
import com.tel_ran.rent_company.service.IRecordService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordController {
    final static String CLERK_RECORDS_PATH = "/clerk/records";
    final static String TECHNICIAN_RECORDS_PATH = "/technician/records";
    @Autowired
    IRecordService recordService;

    @PostMapping(value = CLERK_RECORDS_PATH + "/rent")
    public RentCarDto rentCar(@Valid @RequestBody RentCarDto rentDto) {
        return recordService.rentCar(rentDto);
    }

    @PatchMapping(value = CLERK_RECORDS_PATH + "/return")
    public ReturnCarDto returnCar(@Valid @RequestBody ReturnCarDto returnDto) {
        return recordService.returnCar(returnDto);
    }

    @GetMapping(value = TECHNICIAN_RECORDS_PATH)
    public List<RecordDto> getRecords(@RequestParam(name = "fromDate") String fromDate,
                                      @RequestParam(name = "toDate") String toDate) {
        return recordService.getRecords(fromDate, toDate);
    }
}
