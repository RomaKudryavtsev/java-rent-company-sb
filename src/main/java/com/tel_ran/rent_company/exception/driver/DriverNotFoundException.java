package com.tel_ran.rent_company.exception.driver;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(final String message) {
        super(message);
    }
}
