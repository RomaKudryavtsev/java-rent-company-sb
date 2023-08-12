package com.tel_ran.rent_company.exception.car;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(final String message) {
        super(message);
    }
}
