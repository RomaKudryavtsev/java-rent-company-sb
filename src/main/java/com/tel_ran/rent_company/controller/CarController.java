package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.CarDto;
import com.tel_ran.rent_company.dto.RemoveCarDto;
import com.tel_ran.rent_company.service.ICarService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarController {
    final static String MANAGER_CAR_PATH = "/manager/car/";
    final static String CLERK_CAR_PATH = "/clerk/car";
    final static String DRIVER_CAR_PATH = "/driver/car";
    @Autowired
    ICarService carService;

    @PostMapping(value = MANAGER_CAR_PATH)
    public CarDto addCar(@Valid @RequestBody CarDto carDto) {
        return carService.addCar(carDto);
    }

    @DeleteMapping(value = MANAGER_CAR_PATH + "/{reg_number}")
    public RemoveCarDto removeCarByRegNumber(@PathVariable (name = "reg_number") String regNumber) {
        return carService.removeCarByRegNumber(regNumber);
    }

    @DeleteMapping(value = MANAGER_CAR_PATH + "/model/{model_name}")
    public List<RemoveCarDto> removeCarsByModelName(@PathVariable (name = "model_name") String modelName) {
        return carService.removeCarsByModelName(modelName);
    }

    @GetMapping(value = CLERK_CAR_PATH + "/model/{model_name}")
    public List<CarDto> getCarsByModelName(@PathVariable(name = "model_name") String modelName) {
        return carService.getCarsByModelName(modelName);
    }

    @GetMapping(value = DRIVER_CAR_PATH + "/{reg_number}")
    public CarDto getCarByRegNumber (@PathVariable(name = "reg_number") String regNumber) {
        return carService.getCarByRegNumber(regNumber);
    }

    @GetMapping(value = DRIVER_CAR_PATH + "/driver/{license_id}")
    public List<CarDto> getCarsByDriver(@PathVariable(name = "license_id") String licenseId) {
        return carService.getCarsByDriver(licenseId);
    }
}
