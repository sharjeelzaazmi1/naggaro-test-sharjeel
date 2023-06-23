package com.naggaro.javatest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Getter
@Setter
public class UserSessionDto {
    String clientId;
    String token;
    Date loginTime;

    public UserSessionDto(String clientId, String token, Date loginTime) {
        this.clientId = clientId;
        this.token = token;
        this.loginTime = loginTime;
    }
}
