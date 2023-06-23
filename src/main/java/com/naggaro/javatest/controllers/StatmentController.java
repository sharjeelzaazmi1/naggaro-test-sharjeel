package com.naggaro.javatest.controllers;

import com.naggaro.javatest.dto.GenericResponseDto;
import com.naggaro.javatest.dto.RequestDto;
import com.naggaro.javatest.entity.Statement;
import com.naggaro.javatest.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@RestController
@RequestMapping("/statement")
public class StatmentController {

    @Autowired
    StatementService statementService;

    /**
     *
     The server will handle requests to view statements by performing simple search on date and amount ranges.
     - The request should specify the account id.
     - The request can specify from date and to date (the date range).
     - The request can specify from amount and to amount (the amount range).

     - If the parameters are invalid a proper error message should be sent to user.
     -
     - All the exceptions should be handled on the server properly.
     *
     * @param requestDto
     * @return
     */



    @GetMapping("/get")
    public GenericResponseDto<List<Statement>> getStatment(@RequestBody  (required=false)  @Valid RequestDto requestDto, ServletRequest request) {

        HttpServletRequest req = (HttpServletRequest) request;
        String clientId = req.getHeader("ClientId");


        List<Statement> bankstatements = statementService.getStatement( requestDto,clientId);

        return GenericResponseDto.newSuccessInstance(bankstatements);


    }

}
