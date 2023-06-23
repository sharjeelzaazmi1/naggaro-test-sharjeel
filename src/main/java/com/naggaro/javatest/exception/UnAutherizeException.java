package com.naggaro.javatest.exception;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
public class UnAutherizeException extends RuntimeException {


    public UnAutherizeException(String errorMessage) {
        super(errorMessage);
    }
}
