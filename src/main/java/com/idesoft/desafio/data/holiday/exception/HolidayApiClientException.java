package com.idesoft.desafio.data.holiday.exception;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

public class HolidayApiClientException extends RuntimeException {

    public HolidayApiClientException() {
    }

    public HolidayApiClientException(String message) {
        super(message);
    }
}
