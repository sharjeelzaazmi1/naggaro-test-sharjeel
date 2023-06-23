package com.naggaro.javatest.controllers;

import com.naggaro.javatest.dto.GenericResponseDto;
import com.naggaro.javatest.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@RestController
@RequestMapping("/naggaro/api/v1")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/auth")
    public GenericResponseDto<String> authenticateUser(ServletRequest request, ServletResponse response )  {
      return  loginService.authenticateUser(request);

    }


    @GetMapping("/logout")
    public GenericResponseDto<String> logoutUser(ServletRequest request, ServletResponse response  )  {
       return loginService.logoutUser(request);
    }

}
