package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.record.RecordDto;
import com.tel_ran.rent_company.dto.record.RentCarDto;
import com.tel_ran.rent_company.dto.record.ReturnCarDto;
import com.tel_ran.rent_company.entity.Car;
import com.tel_ran.rent_company.entity.Driver;
import com.tel_ran.rent_company.entity.RentRecord;
import com.tel_ran.rent_company.entity.State;
import com.tel_ran.rent_company.exception.car.CarInUseException;
import com.tel_ran.rent_company.exception.car.CarNotFoundException;
import com.tel_ran.rent_company.exception.car.CarToBeRemovedException;
import com.tel_ran.rent_company.exception.driver.DriverNotFoundException;
import com.tel_ran.rent_company.repo.CarRepo;
import com.tel_ran.rent_company.repo.DriverRepo;
import com.tel_ran.rent_company.repo.RecordRepo;
import com.tel_ran.rent_company.service.ICarService;
import com.tel_ran.rent_company.service.IRecordService;
import com.tel_ran.rent_company.util.DateUtil;
import com.tel_ran.rent_company.util.PageUtil;
import com.tel_ran.rent_company.util.RecordMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
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
    static final int CRITICAL_DAMAGES = 60;
    static final int SERIOUS_DAMAGES = 30;
    static final int LIGHT_DAMAGES = 10;
    @Value("${rent.fine.percent}")
    Integer finePercent;
    @Value("${rent.gas.price}")
    int gasPrice;
    final CarRepo carRepo;
    final DriverRepo driverRepo;
    final RecordRepo recordRepo;
    final DateTimeFormatter formatter;
    final ICarService carService;

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
        updateCarOnRent(car);
        return res;
    }

    @Transactional
    @Override
    public RecordDto returnCar(ReturnCarDto returnDto) {
        Long licenseId = returnDto.getLicenseId();
        String regNumber = returnDto.getRegNumber();
        int damages = returnDto.getDamages();
        checkRecord(licenseId, regNumber);
        Car car = carRepo.findByRegNumber(regNumber);
        RentRecord updatedRecord = updateRecordOnReturn(licenseId, regNumber, returnDto);
        RecordDto res = RecordMapper.entityToRecordDto(recordRepo.save(updatedRecord), formatter);
        updateCarOnReturn(car, damages);
        return res;
    }

    @Override
    public List<RecordDto> getRecords(String fromDate, String toDate, Integer from, Integer size) {
        LocalDate[] dates = DateUtil.parseDates(fromDate, toDate, formatter);
        Pageable pageRequest = PageUtil.makePageRequest(from, size);
        return recordRepo.findAllRecordsBetweenRentDates(dates[0], dates[1], pageRequest).stream()
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
        RentRecord record = RecordMapper.rentCarDtoToEntity(rentDto);
        LocalDate rentDate = DateUtil.parseDate(rentDto.getRentDate(), formatter);
        record.setRentDate(rentDate);
        Driver driver = driverRepo.findByLicenseId(rentDto.getLicenseId());
        record.setDriver(driver);
        record.setCar(car);
        return record;
    }

    private void updateCarOnRent(Car car) {
        car.setInUse(true);
        carRepo.save(car);
    }

    private void updateCarOnReturn(Car car, int damages) {
        car.setInUse(false);
        updateState(car, damages);
        if (damages > CRITICAL_DAMAGES || car.getToBeRemoved()) {
            carService.removeCarByRegNumber(car.getRegNumber());
        } else {
            carRepo.save(car);
        }
    }

    private void updateState(Car car, int damages) {
        if (damages >= LIGHT_DAMAGES && damages < SERIOUS_DAMAGES) {
            car.setState(State.GOOD);
        } else if (damages >= SERIOUS_DAMAGES) {
            car.setState(State.BAD);
        }
    }

    private RentRecord updateRecordOnReturn(Long licenseId, String regNumber, ReturnCarDto returnDto) {
        RentRecord rentRecordFound = recordRepo.findByCar_RegNumberAndDriver_LicenseId(regNumber, licenseId);
        rentRecordFound.setReturnDate(DateUtil.parseDate(returnDto.getReturnDate(), formatter));
        rentRecordFound.setDamages(returnDto.getDamages());
        rentRecordFound.setTankPercent(returnDto.getTankPercent());
        int dayPrice = rentRecordFound.getCar().getModel().getDayPrice();
        int rentDays = rentRecordFound.getRentDays();
        int delay = getDelay(rentRecordFound);
        rentRecordFound.setCost(calculateCost(dayPrice, rentDays, delay, returnDto.getTankPercent(),
                returnDto.getDamages()));
        return rentRecordFound;
    }
}
