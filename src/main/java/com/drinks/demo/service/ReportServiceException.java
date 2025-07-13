package com.drinks.demo.service;

public class ReportServiceException extends Exception {
    public ReportServiceException(String message) {
        super(message);
    }

    public ReportServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
