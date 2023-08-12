package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.AddCarRequestDto;
import com.tel_ran.rent_company.dto.CarResponseDto;
import com.tel_ran.rent_company.dto.RemoveCarDto;
import com.tel_ran.rent_company.entity.Car;
import com.tel_ran.rent_company.entity.State;
import com.tel_ran.rent_company.exception.CarExistsException;
import com.tel_ran.rent_company.exception.ModelNotFoundException;
import com.tel_ran.rent_company.repo.CarRepo;
import com.tel_ran.rent_company.repo.ModelRepo;
import com.tel_ran.rent_company.service.ICarService;
import com.tel_ran.rent_company.util.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements ICarService {
    @Autowired
    CarRepo carRepo;
    @Autowired
    ModelRepo modelRepo;

    private void checkIfCarExists(String regNumber) {
        if (carRepo.existsByRegNumber(regNumber)) {
            throw new CarExistsException("Car already exists");
        }
    }

    private void checkIfModelExists(String modelName) {
        if (!modelRepo.existsByModelName(modelName)) {
            throw new ModelNotFoundException("Model does not exist");
        }
    }

    @Override
    public CarResponseDto addCar(AddCarRequestDto addCarRequestDto) {
        checkIfCarExists(addCarRequestDto.getRegNumber());
        checkIfModelExists(addCarRequestDto.getModelName());
        Car carToBeAdded = CarMapper.requestDtoToEntity(addCarRequestDto);
        carToBeAdded.setInUse(false);
        carToBeAdded.setToBeRemoved(false);
        carToBeAdded.setState(State.EXCELLENT);
        carToBeAdded.setModel(modelRepo.findByModelName(addCarRequestDto.getModelName()));
        return CarMapper.entityToResponseDto(carRepo.save(carToBeAdded));
    }

    @Override
    public RemoveCarDto removeCarByRegNumber(String regNumber) {
        //TODO: to implement after records
        return null;
    }

    @Override
    public List<RemoveCarDto> removeCarsByModelName(String modelName) {
        //TODO: to implement after records
        return null;
    }

    @Override
    public List<CarResponseDto> getCarsByModelName(String modelName) {
        return carRepo.findByModel_ModelName(modelName).stream().map(CarMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto getCarByRegNumber(String regNumber) {
        return CarMapper.entityToResponseDto(carRepo.findByRegNumber(regNumber));
    }

    @Override
    public List<CarResponseDto> getCarsByDriver(Long licenseId) {
        return carRepo.findCarsByDriver(licenseId).stream()
                .map(CarMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }
}
