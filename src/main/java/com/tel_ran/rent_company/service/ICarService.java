package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.car.AddCarRequestDto;
import com.tel_ran.rent_company.dto.car.CarResponseDto;
import com.tel_ran.rent_company.dto.car.RemoveCarDto;

import java.util.List;

public interface ICarService {
    CarResponseDto addCar(AddCarRequestDto addCarRequestDto);

    RemoveCarDto removeCarByRegNumber(String regNumber);

    List<RemoveCarDto> removeCarsByModelName(String modelName);

    List<CarResponseDto> getCarsByModelName(String modelName, Integer from, Integer size);

    CarResponseDto getCarByRegNumber(String regNumber);

    List<CarResponseDto> getCarsByDriver(Long licenseId, Integer from, Integer size);
}
