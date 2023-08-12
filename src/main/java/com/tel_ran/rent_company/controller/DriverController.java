package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.driver.DriverDto;
import com.tel_ran.rent_company.service.IDriverService;
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
@Api(tags = "Drivers")
public class DriverController {
    final static String CLERK_DRIVER_PATH = "/clerk/driver";
    final static String STATIST_DRIVER_PATH = "/statist/driver";
    final static String DRIVER_DRIVER_PATH = "/driver/driver";

    @Autowired
    IDriverService driverService;

    @ApiOperation(value = "Add new driver")
    @PostMapping(value = CLERK_DRIVER_PATH)
    public DriverDto addDriver(@Valid @RequestBody DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @ApiOperation(value = "Find most active drivers (by records number)")
    @GetMapping(value = STATIST_DRIVER_PATH + "/active")
    public List<DriverDto> getMostActiveDrivers() {
        return driverService.getMostActiveDrivers();
    }

    @ApiOperation(value = "Find driver by license id")
    @GetMapping(value = DRIVER_DRIVER_PATH)
    public DriverDto getDriverDataByLicenseId(@RequestParam(name = "license_id") Long licenseId) {
        return driverService.getDriverDataByLicenseId(licenseId);
    }

    @ApiOperation(value = "Find drivers who rented certain car")
    @GetMapping(value = DRIVER_DRIVER_PATH + "/car")
    public List<DriverDto> driversByCar(@RequestParam(name = "reg_number") String regNumber) {
        return driverService.driversByCar(regNumber);
    }
}
