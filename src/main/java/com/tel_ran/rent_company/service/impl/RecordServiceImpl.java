package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.RecordDto;
import com.tel_ran.rent_company.dto.RentCarDto;
import com.tel_ran.rent_company.dto.ReturnCarDto;
import com.tel_ran.rent_company.entity.Car;
import com.tel_ran.rent_company.entity.Driver;
import com.tel_ran.rent_company.entity.RentRecord;
import com.tel_ran.rent_company.exception.CarNotFoundException;
import com.tel_ran.rent_company.exception.DriverNotFoundException;
import com.tel_ran.rent_company.repo.CarRepo;
import com.tel_ran.rent_company.repo.DriverRepo;
import com.tel_ran.rent_company.repo.RecordRepo;
import com.tel_ran.rent_company.service.IRecordService;
import com.tel_ran.rent_company.util.RecordMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordServiceImpl implements IRecordService {
    @Value("${rent.fine.percent}")
    Integer finePercent;
    @Value("${rent.gas.price}")
    int gasPrice;
    @Value("${rent.date.format}")
    String format;
    @Autowired
    CarRepo carRepo;
    @Autowired
    DriverRepo driverRepo;
    @Autowired
    RecordRepo recordRepo;

    private void checkRecord(Long licenseId, String regNumber) {
        if (!driverRepo.existsByLicenseId(licenseId)) {
            throw new DriverNotFoundException("Driver does not exist");
        }
        if (!carRepo.existsByRegNumber(regNumber)) {
            throw new CarNotFoundException("Car does not exist");
        }
    }

    private LocalDate parseDate(String rentDate) {
        return LocalDate.parse(rentDate, DateTimeFormatter.ofPattern(format));
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

    //TODO: car has to be updated on rent and on return
    @Transactional
    @Override
    public RentCarDto rentCar(RentCarDto rentDto) {
        Long licenseId = rentDto.getLicenseId();
        String regNumber = rentDto.getRegNumber();
        checkRecord(licenseId, regNumber);
        RentRecord newRecord = RecordMapper.rentCarDtoToEntity(rentDto, DateTimeFormatter.ofPattern(format));
        LocalDate rentDate = parseDate(rentDto.getRentDate());
        newRecord.setRentDate(rentDate);
        Driver driver = driverRepo.findByLicenseId(licenseId);
        newRecord.setDriver(driver);
        Car car = carRepo.findByRegNumber(regNumber);
        newRecord.setCar(car);
        return RecordMapper.entityToRentCarDto(recordRepo.save(newRecord), DateTimeFormatter.ofPattern(format));
    }

    @Transactional
    @Override
    public RecordDto returnCar(ReturnCarDto returnDto) {
        Long licenseId = returnDto.getLicenseId();
        String regNumber = returnDto.getRegNumber();
        checkRecord(returnDto.getLicenseId(), returnDto.getRegNumber());
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
        return RecordMapper.entityToRecordDto(recordRepo.save(rentRecordFound), DateTimeFormatter.ofPattern(format));
    }

    @Override
    public List<RecordDto> getRecords(String fromDate, String toDate) {
        LocalDate from = parseDate(fromDate);
        LocalDate to = parseDate(toDate);
        return recordRepo.findAllRecordsBetweenRentDates(from, to).stream()
                .map(r -> RecordMapper.entityToRecordDto(r, DateTimeFormatter.ofPattern(format)))
                .collect(Collectors.toList());
    }
}
