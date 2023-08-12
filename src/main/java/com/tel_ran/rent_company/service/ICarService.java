package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.AddCarRequestDto;
import com.tel_ran.rent_company.dto.CarResponseDto;
import com.tel_ran.rent_company.dto.RemoveCarDto;

import java.util.List;

public interface ICarService {
    CarResponseDto addCar(AddCarRequestDto addCarRequestDto);

    RemoveCarDto removeCarByRegNumber(String regNumber);

    List<RemoveCarDto> removeCarsByModelName(String modelName);

    List<CarResponseDto> getCarsByModelName(String modelName);

    CarResponseDto getCarByRegNumber(String regNumber);

    List<CarResponseDto> getCarsByDriver(Long licenseId);
}
