package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.DriverDto;
import com.tel_ran.rent_company.entity.Driver;
import com.tel_ran.rent_company.exception.DriverExistsException;
import com.tel_ran.rent_company.repo.DriverRepo;
import com.tel_ran.rent_company.service.IDriverService;
import com.tel_ran.rent_company.util.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements IDriverService {
    @Autowired
    DriverRepo driverRepo;

    private void checkIfDriverExists(Long licenseId) {
        if(driverRepo.existsByLicenseId(licenseId)) {
            throw new DriverExistsException("Driver already exists");
        }
    }

    @Transactional
    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        checkIfDriverExists(driverDto.getLicenseId());
        Driver driverToBeAdded = DriverMapper.dtoToEntity(driverDto);
        driverToBeAdded.setRecords(new ArrayList<>());
        return DriverMapper.entityToDto(driverRepo.save(driverToBeAdded));
    }

    @Override
    public List<DriverDto> getMostActiveDrivers() {
        //TODO: to implement after records
        return null;
    }

    @Override
    public DriverDto getDriverDataByLicenseId(Long licenseId) {
        return DriverMapper.entityToDto(driverRepo.findByLicenseId(licenseId));
    }

    @Override
    public List<DriverDto> driversByCar(String regNumber) {
        return driverRepo.findDriversByCar(regNumber).stream()
                .map(DriverMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
