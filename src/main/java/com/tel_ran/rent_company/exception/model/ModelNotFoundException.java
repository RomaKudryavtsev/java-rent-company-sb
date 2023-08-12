package com.tel_ran.rent_company.exception.model;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(final String message) {
        super(message);
    }
}
