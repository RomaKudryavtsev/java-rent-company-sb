package com.tel_ran.rent_company.util;

import com.tel_ran.rent_company.dto.driver.DriverDto;
import com.tel_ran.rent_company.entity.Driver;

public class DriverMapper {
    public static Driver dtoToEntity(DriverDto driverDto) {
        Driver driver = new Driver();
        driver.setName(driverDto.getName());
        driver.setPhone(driverDto.getPhone());
        driver.setBirthYear(driverDto.getBirthYear());
        driver.setLicenseId(driverDto.getLicenseId());
        return driver;
    }

    public static DriverDto entityToDto(Driver driver) {
        return DriverDto.builder()
                .driverId(driver.getId())
                .licenseId(driver.getLicenseId())
                .name(driver.getName())
                .phone(driver.getPhone())
                .birthYear(driver.getBirthYear())
                .build();
    }
}
