package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.CarDto;
import com.tel_ran.rent_company.dto.DriverDto;
import com.tel_ran.rent_company.service.IDriverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements IDriverService {
    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        return null;
    }

    @Override
    public List<DriverDto> getMostActiveDrivers() {
        return null;
    }

    @Override
    public DriverDto getDriverDataByLicenseId(Long licenseId) {
        return null;
    }

    @Override
    public List<CarDto> getCarsByDriver(Long licenseId) {
        return null;
    }
}
