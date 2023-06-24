package com.naggaro.javatest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Getter
@Setter
public class GenericResponseDto<T>{
    String resposneCode;
    String description;
    private T body;

    public static <T> GenericResponseDto<T> newSuccessInstance(T body) {
        GenericResponseDto<T> genericDTO = new GenericResponseDto<T>();
        genericDTO.setResposneCode("00");
        genericDTO.setDescription("Operation Success");
        genericDTO.setBody( body);
        return genericDTO;
    }
    public static <T> GenericResponseDto<T> newFailedInstance(T body) {
        GenericResponseDto<T> genericDTO = new GenericResponseDto<T>();
        genericDTO.setResposneCode("02");
        genericDTO.setDescription("Operation Failed");
        genericDTO.setBody(body);
        return genericDTO;
    }



}
