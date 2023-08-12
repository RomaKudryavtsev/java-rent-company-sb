package com.tel_ran.rent_company.util;

import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    private static void validateDates(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new DateTimeException("From date may not be after to date");
        }
    }

    public static LocalDate[] parseDates(String from, String to, DateTimeFormatter formatter) {
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        validateDates(fromDate, toDate);
        return new LocalDate[]{fromDate, toDate};
    }

    public static LocalDate parseDate(String date, DateTimeFormatter formatter) {
        return LocalDate.parse(date, formatter);
    }
}
