package io.patriotframework.virtualsmarthomeplus;

import io.patriotframework.virtualsmarthomeplus.APIErrors.APIError;
import io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;

/**
 * Handles custom exceptions
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Returns proper response entity on MethodArgumentNotValidException.
     *
     * @param ex      exception which caused this method call
     * @param headers http headers
     * @param status  http status
     * @param request web request
     * @return {@link io.patriotframework.virtualsmarthomeplus.APIErrors.APIError APIError} instance whith list of
     * {@link io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError NotValidSubError} instances, which
     * informs about specific validity violations
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final String message = "Malformed JSON body";
        final APIError apiError = new APIError(status, message);

        ex.getBindingResult().getAllErrors().forEach((error) ->
                apiError.addSubError(new NotValidSubError((FieldError) error)));

        return new ResponseEntity<>(apiError, status);
    }
    /**
     * Returns proper response entity on NoSuchElementException.
     *
     * @param ex      exception which caused this method call
     * @return {@link io.patriotframework.virtualsmarthomeplus.APIErrors.APIError APIError} instance whith list of
     * {@link io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError NotValidSubError} instances, which
     * informs about specific validity violations
     */
    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNotFound(NoSuchElementException ex) {
        final String message = "Device not found";
        final APIError apiError = new APIError(HttpStatus.NOT_FOUND,message);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }
    /**
     * Returns proper response entity on IllegalArgumentException.
     *
     * @param ex      exception which caused this method call
     * @return {@link io.patriotframework.virtualsmarthomeplus.APIErrors.APIError APIError} instance whith list of
     * {@link io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError NotValidSubError} instances, which
     * informs about specific validity violations
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleWrongArgument(IllegalArgumentException ex) {
        final String message = "Wrong label";
        final APIError apiError = new APIError(HttpStatus.BAD_REQUEST,message);
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
    /**
     * Returns proper response entity on KeyAlreadyExistException.
     *
     * @param ex      exception which caused this method call
     * @return {@link io.patriotframework.virtualsmarthomeplus.APIErrors.APIError APIError} instance whith list of
     * {@link io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError NotValidSubError} instances, which
     * informs about specific validity violations
     */
    @ExceptionHandler(value = KeyAlreadyExistsException.class)
    protected ResponseEntity<Object> handleExistingDevice(IllegalArgumentException ex) {
        final String message = "Device already exists";
        final APIError apiError = new APIError(HttpStatus.CONFLICT,message);
        return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
    }


}
