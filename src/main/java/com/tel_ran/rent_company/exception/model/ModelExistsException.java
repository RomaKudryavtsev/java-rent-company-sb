package com.tel_ran.rent_company.exception.model;

public class ModelExistsException extends RuntimeException {
    public ModelExistsException(final String message) {
        super(message);
    }
}
