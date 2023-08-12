package com.tel_ran.rent_company.exception.car;

public class CarToBeRemovedException extends RuntimeException {
    public CarToBeRemovedException(final String message) {
        super(message);
    }
}
