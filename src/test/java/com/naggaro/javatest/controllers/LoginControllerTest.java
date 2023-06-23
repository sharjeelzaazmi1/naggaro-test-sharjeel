package com.naggaro.javatest.controllers;

import com.naggaro.javatest.constants.NaggaroConstants;
import com.naggaro.javatest.dto.GenericResponseDto;
import com.naggaro.javatest.exception.UnAutherizeException;
import com.naggaro.javatest.service.LoginService;
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

import javax.servlet.ServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService mockLoginService;

    @Test
    void testAuthenticateUser() throws Exception {
        // Setup
        // Configure LoginService.authenticateUser(...).
        final GenericResponseDto<String> stringGenericResponseDto = GenericResponseDto.newSuccessInstance("TOKEN&&#^$234");
        when(mockLoginService.authenticateUser(any(ServletRequest.class))).thenReturn(stringGenericResponseDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/naggaro/api/v1/auth")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testAuthenticateUser_LoginServiceReturnsFailure() throws Exception {
        // Setup
        // Configure LoginService.authenticateUser(...).
        when(mockLoginService.authenticateUser(any(ServletRequest.class))).thenThrow(new UnAutherizeException("Authentication failed"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/naggaro/api/v1/auth")
                .header("ClientId","User")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void testLogoutUser() throws Exception {
        // Setup
        // Configure LoginService.logoutUser(...).
        final GenericResponseDto<String> stringGenericResponseDto = GenericResponseDto.newSuccessInstance(NaggaroConstants.LOGOUT_TEXT);
        when(mockLoginService.logoutUser(any(ServletRequest.class))).thenReturn(stringGenericResponseDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/naggaro/api/v1/logout")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

    @Test
    void testLogoutUser_LoginServiceReturnsFailure() throws Exception {
        // Setup

        // Configure LoginService.logoutUser(...).
        final GenericResponseDto<String> stringGenericResponseDto = GenericResponseDto.newFailedInstance(NaggaroConstants.USER_NOT_FOUND);
        when(mockLoginService.logoutUser(any(ServletRequest.class))).thenReturn(stringGenericResponseDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/naggaro/api/v1/logout")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
