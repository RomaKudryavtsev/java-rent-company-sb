package com.tel_ran.rent_company.exception.driver;

public class DriverExistsException extends RuntimeException {
    public DriverExistsException(final String message) {
        super(message);
    }
}
