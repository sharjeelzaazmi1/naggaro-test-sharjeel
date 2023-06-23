package com.naggaro.javatest.controllers;

import com.naggaro.javatest.dto.RequestDto;
import com.naggaro.javatest.entity.Account;
import com.naggaro.javatest.entity.Statement;
import com.naggaro.javatest.service.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatmentController.class)
class StatmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementService mockStatementService;

    @Test
    void testGetStatment() throws Exception {
        // Setup
        // Configure StatementService.getStatement(...).
        final List<Statement> statements = Arrays.asList(new Statement(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new BigDecimal("0.00"), new Account("","")));
        when(mockStatementService.getStatement(any(RequestDto.class), eq("clientId"))).thenReturn(statements);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/statement/get")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        testGetStatment_StatementServiceReturnsNoItems();
    }

    @Test
    void testGetStatment_StatementServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockStatementService.getStatement(any(RequestDto.class), eq("clientId"))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/statement/get")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
