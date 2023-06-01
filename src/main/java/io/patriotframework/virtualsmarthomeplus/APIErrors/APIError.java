package io.patriotframework.virtualsmarthomeplus.APIErrors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents api error.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private final String message;
    private List<APISubError> subErrors;

    /**
     * Constructs instance of APIError with current time, error message and
     * @param status status for the recipient
     * @param message message for the recipient
     */
    public APIError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    /**
     * Adds instance of APISubError to the list of errors which caused this error
     * @param error another sub-error which caused error
     */
    public void addSubError(APISubError error) {
        if(subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(error);
    }
}
