package com.ahad.devicemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(DuplicateDeviceException.class)
    public ResponseEntity<Object> handleInvalidArtistException(DuplicateDeviceException ex) {
        ApiResponse errorResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Object> handleInvalidArtistException(DeviceNotFoundException ex) {
        logger.error(ex.getMessage());
        ApiResponse errorResponse = new ApiResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }


}
