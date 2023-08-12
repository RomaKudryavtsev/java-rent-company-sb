package com.tel_ran.rent_company.exception;

import com.tel_ran.rent_company.exception.car.CarExistsException;
import com.tel_ran.rent_company.exception.car.CarInUseException;
import com.tel_ran.rent_company.exception.car.CarNotFoundException;
import com.tel_ran.rent_company.exception.car.CarToBeRemovedException;
import com.tel_ran.rent_company.exception.driver.DriverExistsException;
import com.tel_ran.rent_company.exception.driver.DriverNotFoundException;
import com.tel_ran.rent_company.exception.model.ModelExistsException;
import com.tel_ran.rent_company.exception.model.ModelNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({
            CarNotFoundException.class,
            DriverNotFoundException.class,
            ModelNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundExceptions(final RuntimeException e) {
        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            CarExistsException.class,
            DriverExistsException.class,
            ModelExistsException.class,
            DateTimeParseException.class,
            CarInUseException.class,
            CarToBeRemovedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(final RuntimeException e) {
        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable t) {
        log.error(t.getMessage());
        return new ErrorResponse(t.getMessage());
    }
}
