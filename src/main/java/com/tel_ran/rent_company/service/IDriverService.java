package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.AddCarRequestDto;
import com.tel_ran.rent_company.dto.DriverDto;

import java.util.List;

public interface IDriverService {
    DriverDto addDriver(DriverDto driverDto);

    List<DriverDto> getMostActiveDrivers();

    DriverDto getDriverDataByLicenseId(Long licenseId);

    List<DriverDto> driversByCar(String regNumber);
}
