package io.patriotframework.virtualsmarthomeplus;

import io.patriotframework.virtualsmarthomeplus.APIErrors.APIError;
import io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles custom exceptions
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Returns proper response entity on MethodArgumentNotValidException.
     * @param ex exception which caused this method call
     * @param headers http headers
     * @param status http status
     * @param request web request
     * @return {@link io.patriotframework.virtualsmarthomeplus.APIErrors.APIError APIError} instance whith list of
     *  {@link io.patriotframework.virtualsmarthomeplus.APIErrors.NotValidSubError NotValidSubError} instances, which
     *  informs about specific validity violations
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = "Malformed JSON body";
        APIError apiError = new APIError(status, message);

        ex.getBindingResult().getAllErrors().forEach((error) ->
                apiError.addSubError(new NotValidSubError((FieldError)error)));

        return new ResponseEntity<>(apiError, status);
    }
}
