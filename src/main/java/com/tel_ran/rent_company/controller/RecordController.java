package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.record.RecordDto;
import com.tel_ran.rent_company.dto.record.RentCarDto;
import com.tel_ran.rent_company.dto.record.ReturnCarDto;
import com.tel_ran.rent_company.service.IRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Api(tags = "Records")
public class RecordController {
    final static String CLERK_RECORDS_PATH = "/clerk/records";
    final static String TECHNICIAN_RECORDS_PATH = "/technician/records";
    @Autowired
    IRecordService recordService;

    @ApiOperation(value = "Rent car")
    @PostMapping(value = CLERK_RECORDS_PATH + "/rent")
    public RentCarDto rentCar(@Valid @RequestBody RentCarDto rentDto) {
        return recordService.rentCar(rentDto);
    }

    @ApiOperation(value = "Return car")
    @PatchMapping(value = CLERK_RECORDS_PATH + "/return")
    public RecordDto returnCar(@Valid @RequestBody ReturnCarDto returnDto) {
        return recordService.returnCar(returnDto);
    }

    @ApiOperation(value = "Get records between dates")
    @GetMapping(value = TECHNICIAN_RECORDS_PATH)
    public List<RecordDto> getRecords(@RequestParam(name = "fromDate") String fromDate,
                                      @RequestParam(name = "toDate") String toDate,
                                      @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return recordService.getRecords(fromDate, toDate, from, size);
    }
}
