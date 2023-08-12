package com.tel_ran.rent_company.exception;

public class CarExistsException extends RuntimeException {
    public CarExistsException(final String message) {
        super(message);
    }
}
