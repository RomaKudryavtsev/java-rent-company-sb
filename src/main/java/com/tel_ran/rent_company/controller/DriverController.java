package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.CarDto;
import com.tel_ran.rent_company.dto.DriverDto;
import com.tel_ran.rent_company.service.IDriverService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverController {
    final static String CLERK_DRIVER_PATH = "/clerk/driver";
    final static String STATIST_DRIVER_PATH = "/statist/driver";
    final static String DRIVER_DRIVER_PATH = "/driver/driver";

    @Autowired
    IDriverService driverService;

    @PostMapping(value = CLERK_DRIVER_PATH)
    public DriverDto addDriver (@Valid @RequestBody DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @GetMapping(value = STATIST_DRIVER_PATH + "/active")
    public List<DriverDto> getMostActiveDrivers() {
        return driverService.getMostActiveDrivers();
    }

    @GetMapping(value = DRIVER_DRIVER_PATH + "/{license_id}")
    public DriverDto getDriverDataByLicenseId (@PathVariable(name = "license_id") Long licenseId) {
        return driverService.getDriverDataByLicenseId(licenseId);
    }

    @GetMapping(value = DRIVER_DRIVER_PATH + "/{license_id}")
    public List<CarDto> getCarsByDriver(@PathVariable(name = "license_id") Long licenseId) {
        return driverService.getCarsByDriver(licenseId);
    }


}
