package io.patriotframework.virtualsmarthomeplus.Mapper;

/**
 * Class is thrown when the ModelMapper doesn't know the type of device.
 */
public class DeviceMappingNotSupportedException extends RuntimeException {
    public DeviceMappingNotSupportedException() {
    }

    public DeviceMappingNotSupportedException(String message) {
        super(message);
    }

    public DeviceMappingNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceMappingNotSupportedException(Throwable cause) {
        super(cause);
    }

    public DeviceMappingNotSupportedException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
