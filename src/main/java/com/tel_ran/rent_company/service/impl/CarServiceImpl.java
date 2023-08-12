package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.car.AddCarRequestDto;
import com.tel_ran.rent_company.dto.car.CarResponseDto;
import com.tel_ran.rent_company.dto.car.RemoveCarDto;
import com.tel_ran.rent_company.entity.Car;
import com.tel_ran.rent_company.entity.RentRecord;
import com.tel_ran.rent_company.entity.State;
import com.tel_ran.rent_company.exception.car.CarExistsException;
import com.tel_ran.rent_company.exception.car.CarNotFoundException;
import com.tel_ran.rent_company.exception.model.ModelNotFoundException;
import com.tel_ran.rent_company.repo.CarRepo;
import com.tel_ran.rent_company.repo.ModelRepo;
import com.tel_ran.rent_company.repo.RecordRepo;
import com.tel_ran.rent_company.service.ICarService;
import com.tel_ran.rent_company.util.CarMapper;
import com.tel_ran.rent_company.util.PageUtil;
import com.tel_ran.rent_company.util.RecordMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarServiceImpl implements ICarService {
    CarRepo carRepo;
    ModelRepo modelRepo;
    RecordRepo recordRepo;
    DateTimeFormatter formatter;

    @Transactional
    @Override
    public CarResponseDto addCar(AddCarRequestDto addCarRequestDto) {
        checkIfCarDoesNotExist(addCarRequestDto.getRegNumber());
        checkIfModelExists(addCarRequestDto.getModelName());
        Car carToBeAdded = CarMapper.requestDtoToEntity(addCarRequestDto);
        carToBeAdded.setInUse(false);
        carToBeAdded.setToBeRemoved(false);
        carToBeAdded.setState(State.EXCELLENT);
        carToBeAdded.setModel(modelRepo.findByModelName(addCarRequestDto.getModelName()));
        carToBeAdded.setRecords(new ArrayList<>());
        return CarMapper.entityToResponseDto(carRepo.save(carToBeAdded));
    }

    @Transactional
    @Override
    public RemoveCarDto removeCarByRegNumber(String regNumber) {
        checkIfCarExists(regNumber);
        List<RentRecord> recordsToBeRemoved = recordRepo.findAllByCar_RegNumber(regNumber);
        Car carToBeDeleted = carRepo.findByRegNumber(regNumber);
        carToBeDeleted.setToBeRemoved(true);
        carRepo.save(carToBeDeleted);
        if (carToBeDeleted.getInUse()) {
            return new RemoveCarDto(CarMapper.entityToResponseDto(carRepo.findByRegNumber(regNumber)), new ArrayList<>());
        } else {
            RemoveCarDto res = new RemoveCarDto(CarMapper.entityToResponseDto(carRepo.findByRegNumber(regNumber)),
                    recordsToBeRemoved.stream()
                            .map(r -> RecordMapper.entityToRecordDto(r, formatter))
                            .collect(Collectors.toList()));
            recordRepo.deleteAll(recordsToBeRemoved);
            carRepo.delete(carToBeDeleted);
            return res;
        }
    }

    @Transactional
    @Override
    public List<RemoveCarDto> removeCarsByModelName(String modelName) {
        return carRepo.findByModel_ModelName(modelName).stream()
                .map(c -> this.removeCarByRegNumber(c.getRegNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsByModelName(String modelName, Integer from, Integer size) {
        Pageable pageRequest = PageUtil.makePageRequest(from, size);
        return carRepo.findByModel_ModelName(modelName, pageRequest).stream().map(CarMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto getCarByRegNumber(String regNumber) {
        checkIfCarExists(regNumber);
        return CarMapper.entityToResponseDto(carRepo.findByRegNumber(regNumber));
    }

    @Override
    public List<CarResponseDto> getCarsByDriver(Long licenseId, Integer from, Integer size) {
        Pageable pageRequest = PageUtil.makePageRequest(from, size);
        return carRepo.findCarsByDriver(licenseId, pageRequest).stream()
                .map(CarMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    private void checkIfCarDoesNotExist(String regNumber) {
        if (carRepo.existsByRegNumber(regNumber)) {
            throw new CarExistsException("Car already exists");
        }
    }

    private void checkIfCarExists(String regNumber) {
        if (!carRepo.existsByRegNumber(regNumber)) {
            throw new CarNotFoundException("Car was not found");
        }
    }

    private void checkIfModelExists(String modelName) {
        if (!modelRepo.existsByModelName(modelName)) {
            throw new ModelNotFoundException("Model does not exist");
        }
    }
}
