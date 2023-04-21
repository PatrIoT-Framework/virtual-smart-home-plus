package io.patriotframework.virtualsmarthomeplus.APIErrors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.FieldError;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class NotValidSubError extends APISubError {

    /**
     * object name
     */
    private String object;
    /**
     * field where the error occurs
     */
    private String field;
    /**
     * rejected value
     */
    private Object rejectedValue;
    /**
     * default message what causes the error
     */
    private String message;

    /**
     * Creates instance of NotValidSubError with initialized attributes. Values are parsed from FieldError
     * @param fieldError instance of FieldError
     */
    public NotValidSubError(FieldError fieldError) {
        this.object = fieldError.getObjectName();
        this.field = fieldError.getField();
        this.rejectedValue = fieldError.getRejectedValue();
        this.message = fieldError.getDefaultMessage();
    }
}
