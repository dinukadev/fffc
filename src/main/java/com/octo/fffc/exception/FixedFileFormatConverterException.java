package com.octo.fffc.exception;

/**
 * This exception class will be used across the application so that checked exceptions will be wrapped
 * within a runtime exception.
 *
 * @author dinuka
 */
public class FixedFileFormatConverterException extends RuntimeException {
    public FixedFileFormatConverterException(Throwable cause) {
        super(cause);
    }

    public FixedFileFormatConverterException(String message) {
        super(message);
    }
}
