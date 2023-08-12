package com.tel_ran.rent_company.util;

import com.tel_ran.rent_company.dto.car.AddCarRequestDto;
import com.tel_ran.rent_company.dto.car.CarResponseDto;
import com.tel_ran.rent_company.entity.Car;

public class CarMapper {
    public static Car requestDtoToEntity(AddCarRequestDto requestDto) {
        Car car = new Car();
        car.setColor(requestDto.getColor());
        car.setRegNumber(requestDto.getRegNumber());
        return car;
    }

    public static CarResponseDto entityToResponseDto(Car car) {
        return CarResponseDto.builder()
                .carId(car.getId())
                .state(car.getState().toString())
                .color(car.getColor())
                .regNumber(car.getRegNumber())
                .modelName(car.getModel().getModelName())
                .inUse(car.getInUse())
                .toBeRemoved(car.getToBeRemoved())
                .build();
    }
}
