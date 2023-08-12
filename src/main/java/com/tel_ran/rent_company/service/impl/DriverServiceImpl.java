package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.driver.DriverDto;
import com.tel_ran.rent_company.entity.Driver;
import com.tel_ran.rent_company.exception.driver.DriverExistsException;
import com.tel_ran.rent_company.exception.driver.DriverNotFoundException;
import com.tel_ran.rent_company.repo.DriverRepo;
import com.tel_ran.rent_company.service.IDriverService;
import com.tel_ran.rent_company.util.DriverMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverServiceImpl implements IDriverService {
    DriverRepo driverRepo;

    @Transactional
    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        checkIfDriverDoesNotExist(driverDto.getLicenseId());
        Driver driverToBeAdded = DriverMapper.dtoToEntity(driverDto);
        driverToBeAdded.setRecords(new ArrayList<>());
        return DriverMapper.entityToDto(driverRepo.save(driverToBeAdded));
    }

    @Override
    public List<DriverDto> getMostActiveDrivers() {
        return driverRepo.findMostActiveDrivers().stream()
                .map(DriverMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDto getDriverDataByLicenseId(Long licenseId) {
        checkIfDriverExists(licenseId);
        return DriverMapper.entityToDto(driverRepo.findByLicenseId(licenseId));
    }

    @Override
    public List<DriverDto> driversByCar(String regNumber) {
        return driverRepo.findDriversByCar(regNumber).stream()
                .map(DriverMapper::entityToDto)
                .collect(Collectors.toList());
    }

    private void checkIfDriverDoesNotExist(Long licenseId) {
        if (driverRepo.existsByLicenseId(licenseId)) {
            throw new DriverExistsException("Driver already exists");
        }
    }

    private void checkIfDriverExists(Long licenseId) {
        if (driverRepo.existsByLicenseId(licenseId)) {
            throw new DriverNotFoundException("Driver does not exist");
        }
    }
}
