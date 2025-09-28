package com.pm.patient_service.exception;

public class EMailAlreadyExistsException extends RuntimeException {
    public EMailAlreadyExistsException(String message) {
        super(message);
    }
}
