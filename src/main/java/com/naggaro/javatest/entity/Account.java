package com.naggaro.javatest.entity;

import com.naggaro.javatest.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String accountType;

    private String accountNumber;

    public String getAccountNumber() {

        return StringUtils.getHash(accountNumber.getBytes())+"";

    }

    public Account()
    {

    }
    public Account(String accountType, String accountNumber) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }
}


