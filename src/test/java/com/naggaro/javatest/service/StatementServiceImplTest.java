package com.naggaro.javatest.service;

import com.naggaro.javatest.dto.RequestDto;
import com.naggaro.javatest.entity.Account;
import com.naggaro.javatest.entity.Statement;
import com.naggaro.javatest.exception.UnAutherizeException;
import com.naggaro.javatest.repository.StatementRepository;
import com.naggaro.javatest.service.impl.StatementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatementServiceImplTest {

    private StatementServiceImpl statementServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        statementServiceImplUnderTest = new StatementServiceImpl();
        statementServiceImplUnderTest.statementRepository = mock(StatementRepository.class);
    }

    @Test
    void testGetStatement() {
        // Setup
        final RequestDto requestDto = new RequestDto();
        requestDto.setAccountId("accountId");
        requestDto.setFromDate("fromDate");
        requestDto.setToDate("toDate");
        requestDto.setFromAmount(new BigDecimal("0.00"));
        requestDto.setToAmount(new BigDecimal("0.00"));

        // Configure StatementRepository.findAll(...).
        final List<Statement> statements = Arrays.asList(new Statement(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new BigDecimal("0.00"), new Account("","")));
        when(statementServiceImplUnderTest.statementRepository.findAll(any(Specification.class))).thenReturn(statements);

        // Run the test
        final List<Statement> result = statementServiceImplUnderTest.getStatement(requestDto, "clientId");

        // Verify the results
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetStatement_StatementRepositoryReturnsNoItems() {
        // Setup
        final RequestDto requestDto = new RequestDto();
        requestDto.setAccountId("accountId");
        requestDto.setFromDate("fromDate");
        requestDto.setToDate("toDate");
        requestDto.setFromAmount(new BigDecimal("0.00"));
        requestDto.setToAmount(new BigDecimal("0.00"));

        when(statementServiceImplUnderTest.statementRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Statement> result = statementServiceImplUnderTest.getStatement(requestDto, "clientId");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testGetStatement_ThrowsUnAutherizeException() {
        // Setup
        final RequestDto requestDto = new RequestDto();
        requestDto.setAccountId("accountId");
        requestDto.setFromDate("fromDate");
        requestDto.setToDate("toDate");
        requestDto.setFromAmount(new BigDecimal("0.00"));
        requestDto.setToAmount(new BigDecimal("0.00"));

        // Configure StatementRepository.findAll(...).
        when(statementServiceImplUnderTest.statementRepository.findAll(any(Specification.class))).thenThrow(new UnAutherizeException(""));

        // Run the test
        assertThatThrownBy(() -> statementServiceImplUnderTest.getStatement(requestDto, "clientId")).isInstanceOf(UnAutherizeException.class);
    }

}
