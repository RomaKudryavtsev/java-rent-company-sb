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
    public List<DriverDto> getMostActiveDrivers(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return driverService.getMostActiveDrivers(from, size);
    }

    @ApiOperation(value = "Find driver by license id")
    @GetMapping(value = DRIVER_DRIVER_PATH + "/{license_id}")
    public DriverDto getDriverDataByLicenseId(@PathVariable(name = "license_id") Long licenseId) {
        return driverService.getDriverDataByLicenseId(licenseId);
    }

    @ApiOperation(value = "Find drivers who rented certain car")
    @GetMapping(value = DRIVER_DRIVER_PATH + "/car/{reg_number}")
    public List<DriverDto> driversByCar(@PathVariable(name = "reg_number") String regNumber,
                                        @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return driverService.driversByCar(regNumber, from, size);
    }
}
