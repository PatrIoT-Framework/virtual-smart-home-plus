package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ErrorMessage("Invalid label of device!");
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage noSuchElementException(NoSuchElementException ex, WebRequest request) {
        return new ErrorMessage("Device not found!");
    }

    @ExceptionHandler(KeyAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage keyAlreadyExistException(KeyAlreadyExistsException ex, WebRequest request) {
        return new ErrorMessage("Device already exists!");
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage typeMismatchException(TypeMismatchException ex, WebRequest request) {
        if (ex.getRequiredType() == null ||
                ex.getValue() == null) {
            return null;
        }
        return new ErrorMessage(
                "Device " + ((Device) ex.getValue()).getLabel() +
                        "is of different type(" + ex.getRequiredType().getTypeName() + ")!");
    }
}
