package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.CarDto;
import com.tel_ran.rent_company.dto.RemoveCarDto;
import com.tel_ran.rent_company.service.ICarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements ICarService {
    @Override
    public CarDto addCar(CarDto carDto) {
        return null;
    }

    @Override
    public RemoveCarDto removeCarByRegNumber(String regNumber) {
        return null;
    }

    @Override
    public List<RemoveCarDto> removeCarsByModelName(String modelName) {
        return null;
    }

    @Override
    public List<CarDto> getCarsByModelName(String modelName) {
        return null;
    }

    @Override
    public CarDto getCarByRegNumber(String regNumber) {
        return null;
    }

    @Override
    public List<CarDto> getCarsByDriver(String licenseId) {
        return null;
    }
}
