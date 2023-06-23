package com.naggaro.javatest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.naggaro.javatest.constants.NaggaroConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Entity
@Getter
@Setter
public class Statement {
    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(pattern= NaggaroConstants.DATE_TIME_FORMATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datefield;

    private BigDecimal amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    public Statement(Date datefield, BigDecimal amount, Account accountId) {
        this.datefield = datefield;
        this.amount = amount;
        this.account = accountId;
    }
    public Statement()
    {}
}
