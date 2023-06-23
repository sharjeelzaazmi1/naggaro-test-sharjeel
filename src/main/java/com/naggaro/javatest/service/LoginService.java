package com.naggaro.javatest.service;

import com.naggaro.javatest.dto.GenericResponseDto;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Service
public interface LoginService {

     GenericResponseDto<String> authenticateUser(ServletRequest request )  ;
     GenericResponseDto<String> logoutUser(ServletRequest request );
}
