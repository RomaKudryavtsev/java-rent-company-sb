package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.CarDto;
import com.tel_ran.rent_company.dto.RemoveCarDto;

import java.util.List;

public interface ICarService {
    CarDto addCar(CarDto carDto);

    RemoveCarDto removeCarByRegNumber(String regNumber);

    List<RemoveCarDto> removeCarsByModelName(String modelName);

    List<CarDto> getCarsByModelName(String modelName);

    CarDto getCarByRegNumber(String regNumber);

    List<CarDto> getCarsByDriver(String licenseId);
}
