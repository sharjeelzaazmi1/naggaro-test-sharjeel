package com.naggaro.javatest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Setter
@Getter
public class ErrorDto {
    int status;
    String error;
    String message;
    String path;
    Date timestamp;

    public ErrorDto(int status ,String error, String message, String path) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = new Date();
        this.status=status;
    }
}
