package com.naggaro.javatest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Getter
@Setter
public class RequestDto {


    String accountId;
    String fromDate;
    String toDate;
    BigDecimal fromAmount;
    BigDecimal toAmount;



    public boolean isEmpty() {
        if(accountId!=null)
            return false;
        if(fromDate!=null)
            return false;
        if(toDate!=null)
            return false;
        if(fromAmount!=null)
            return false;
        if(toAmount!=null)
            return false;

        return true;
    }
}

