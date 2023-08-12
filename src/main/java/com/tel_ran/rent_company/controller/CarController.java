package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.car.AddCarRequestDto;
import com.tel_ran.rent_company.dto.car.CarResponseDto;
import com.tel_ran.rent_company.dto.car.RemoveCarDto;
import com.tel_ran.rent_company.service.ICarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Api(tags = "Cars")
public class CarController {
    final static String MANAGER_CAR_PATH = "/manager/car/";
    final static String CLERK_CAR_PATH = "/clerk/car";
    final static String DRIVER_CAR_PATH = "/driver/car";
    @Autowired
    ICarService carService;

    @ApiOperation(value = "Add new car")
    @PostMapping(value = MANAGER_CAR_PATH)
    public CarResponseDto addCar(@Valid @RequestBody AddCarRequestDto addCarRequestDto) {
        return carService.addCar(addCarRequestDto);
    }

    @ApiOperation(value = "Delete car by registration number")
    @DeleteMapping(value = MANAGER_CAR_PATH + "/{reg_number}")
    public RemoveCarDto removeCarByRegNumber(@PathVariable(name = "reg_number") String regNumber) {
        return carService.removeCarByRegNumber(regNumber);
    }

    @ApiOperation(value = "Delete cars of certain model")
    @DeleteMapping(value = MANAGER_CAR_PATH + "/model/{model_name}")
    public List<RemoveCarDto> removeCarsByModelName(@PathVariable(name = "model_name") String modelName) {
        return carService.removeCarsByModelName(modelName);
    }

    @ApiOperation(value = "Find cars of certain model")
    @GetMapping(value = CLERK_CAR_PATH + "/model/{model_name}")
    public List<CarResponseDto> getCarsByModelName(@PathVariable(name = "model_name") String modelName,
                                                   @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return carService.getCarsByModelName(modelName, from, size);
    }

    @ApiOperation(value = "Find car by registration number")
    @GetMapping(value = DRIVER_CAR_PATH + "/{reg_number}")
    public CarResponseDto getCarByRegNumber(@PathVariable(name = "reg_number") String regNumber) {
        return carService.getCarByRegNumber(regNumber);
    }

    @ApiOperation(value = "Find cars rented by certain driver")
    @GetMapping(value = DRIVER_CAR_PATH + "/driver/{license_id}")
    public List<CarResponseDto> getCarsByDriver(@PathVariable(name = "license_id") Long licenseId,
                                                @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return carService.getCarsByDriver(licenseId, from, size);
    }
}
