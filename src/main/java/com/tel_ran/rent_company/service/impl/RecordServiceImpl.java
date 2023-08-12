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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecordServiceImpl implements IRecordService {
    @Value("${rent.date.format}")
    String format;
    CarRepo carRepo;
    DriverRepo driverRepo;
    RecordRepo recordRepo;

    private void checkRecord(RentCarDto rentCarDto) {
        if(!driverRepo.existsByLicenseId(rentCarDto.getLicenseId())) {
            throw new DriverNotFoundException("Driver does not exist");
        }
        if(!carRepo.existsByRegNumber(rentCarDto.getRegNumber())) {
            throw new CarNotFoundException("Car does not exist");
        }
    }

    private LocalDate parseDate(String rentDate) {
        return LocalDate.parse(rentDate, DateTimeFormatter.ofPattern(format));
    }

    @Override
    public RentCarDto rentCar(RentCarDto rentDto) {
        checkRecord(rentDto);
        RentRecord newRecord = RecordMapper.rentCarDtoToEntity(rentDto, DateTimeFormatter.ofPattern(format));
        LocalDate rentDate = parseDate(rentDto.getRentDate());
        newRecord.setRentDate(rentDate);
        Driver driver = driverRepo.findByLicenseId(rentDto.getLicenseId());
        newRecord.setDriver(driver);
        Car car = carRepo.findByRegNumber(rentDto.getRegNumber());
        newRecord.setCar(car);
        return RecordMapper.entityToRentCarDto(recordRepo.save(newRecord), DateTimeFormatter.ofPattern(format));
    }

    @Override
    public ReturnCarDto returnCar(ReturnCarDto returnDto) {
        return null;
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
