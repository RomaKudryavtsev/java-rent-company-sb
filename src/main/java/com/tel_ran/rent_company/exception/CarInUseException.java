package com.tel_ran.rent_company.exception;

public class CarInUseException extends RuntimeException {
    public CarInUseException(final String message) {
        super(message);
    }
}
