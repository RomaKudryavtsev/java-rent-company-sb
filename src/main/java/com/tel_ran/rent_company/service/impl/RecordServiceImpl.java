package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.RecordDto;
import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.dto.ReturnCarDto;
import com.tel_ran.rent_company.entity.Car;
import com.tel_ran.rent_company.entity.Driver;
import com.tel_ran.rent_company.entity.RentRecord;
import com.tel_ran.rent_company.exception.CarInUseException;
import com.tel_ran.rent_company.exception.CarNotFoundException;
import com.tel_ran.rent_company.exception.CarToBeRemovedException;
import com.tel_ran.rent_company.exception.DriverNotFoundException;
import com.tel_ran.rent_company.repo.CarRepo;
import com.tel_ran.rent_company.repo.DriverRepo;
import com.tel_ran.rent_company.repo.RecordRepo;
import com.tel_ran.rent_company.service.IRecordService;
import com.tel_ran.rent_company.util.DateUtil;
import com.tel_ran.rent_company.util.RecordMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordServiceImpl implements IRecordService {
    @Value("${rent.fine.percent}")
    Integer finePercent;
    @Value("${rent.gas.price}")
    int gasPrice;
    final CarRepo carRepo;
    final DriverRepo driverRepo;
    final RecordRepo recordRepo;
    final DateTimeFormatter formatter;

    @Transactional
    @Override
    public RentCarDto rentCar(RentCarDto rentDto) {
        Long licenseId = rentDto.getLicenseId();
        String regNumber = rentDto.getRegNumber();
        checkRecord(licenseId, regNumber);
        Car car = carRepo.findByRegNumber(regNumber);
        checkCar(car);
        RentRecord newRecord = setNewRecordOnRent(rentDto, formatter, car);
        RentCarDto res = RecordMapper.entityToRentCarDto(recordRepo.save(newRecord), formatter);
        updateCar(car, true);
        return res;
    }

    @Transactional
    @Override
    public RecordDto returnCar(ReturnCarDto returnDto) {
        Long licenseId = returnDto.getLicenseId();
        String regNumber = returnDto.getRegNumber();
        checkRecord(licenseId, regNumber);
        RentRecord rentRecordFound = updateRecordOnReturn(licenseId, regNumber, returnDto);
        RecordDto res = RecordMapper.entityToRecordDto(recordRepo.save(rentRecordFound), formatter);
        updateCar(carRepo.findByRegNumber(regNumber), false);
        return res;
    }

    @Override
    public List<RecordDto> getRecords(String fromDate, String toDate) {
        LocalDate[] dates = DateUtil.parseDates(fromDate, toDate, formatter);
        return recordRepo.findAllRecordsBetweenRentDates(dates[0], dates[1]).stream()
                .map(r -> RecordMapper.entityToRecordDto(r, formatter))
                .collect(Collectors.toList());
    }

    private void checkRecord(Long licenseId, String regNumber) {
        if (!driverRepo.existsByLicenseId(licenseId)) {
            throw new DriverNotFoundException("Driver does not exist");
        }
        if (!carRepo.existsByRegNumber(regNumber)) {
            throw new CarNotFoundException("Car does not exist");
        }
    }

    private void checkCar(Car car) {
        if (car.getInUse()) {
            throw new CarInUseException("Car is already rented");
        }
        if (car.getToBeRemoved()) {
            throw new CarToBeRemovedException("Car is marked to be removed and may not be rented");
        }
    }

    private double calculateCost(int rentPrice, int rentDays, int delay, int tankPercent, int tankVolume) {
        double cost = rentPrice * rentDays;
        if (delay > 0) {
            cost += delay * (rentPrice * (1 + (double) finePercent / 100));
        }
        if (tankPercent < 100) {
            cost += tankVolume * ((double) (100 - tankPercent) / 100) * gasPrice;
        }
        return cost;
    }

    private int getDelay(RentRecord record) {
        long realDays = ChronoUnit.DAYS.between(record.getRentDate(), record.getReturnDate());
        int diff = (int) realDays - record.getRentDays();
        return Math.max(diff, 0);
    }

    private RentRecord setNewRecordOnRent(RentCarDto rentDto, DateTimeFormatter formatter, Car car) {
        RentRecord record = RecordMapper.rentCarDtoToEntity(rentDto, formatter);
        LocalDate rentDate = DateUtil.parseDate(rentDto.getRentDate(), formatter);
        record.setRentDate(rentDate);
        Driver driver = driverRepo.findByLicenseId(rentDto.getLicenseId());
        record.setDriver(driver);
        record.setCar(car);
        return record;
    }

    private void updateCar(Car car, boolean onRent) {
        if (onRent) {
            car.setInUse(true);
            carRepo.save(car);
        } else {
            car.setInUse(false);
            carRepo.save(car);
        }
    }

    private RentRecord updateRecordOnReturn(Long licenseId, String regNumber, ReturnCarDto returnDto) {
        RentRecord rentRecordFound = recordRepo.findByCar_RegNumberAndDriver_LicenseId(regNumber, licenseId);
        rentRecordFound.setDamages(returnDto.getDamages());
        rentRecordFound.setTankPercent(returnDto.getTankPercent());
        rentRecordFound.setCost(calculateCost(
                rentRecordFound.getCar().getModel().getDayPrice(),
                rentRecordFound.getRentDays(),
                getDelay(rentRecordFound),
                returnDto.getTankPercent(),
                returnDto.getDamages()
        ));
        return rentRecordFound;
    }
}
