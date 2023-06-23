package com.naggaro.javatest.exception;

import com.naggaro.javatest.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {


    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerAdvice.class);

    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value= HttpStatus.CONFLICT,
            reason="Data integrity violation")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
        // Nothing to do
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError() {

        return "databaseError";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleError(HttpServletRequest req, Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

       // logger.error("Error while handling request " + req.getServletPath() ,ex);
        return handleExceptionInternal(ex, new ErrorDto(status.value(),"Internal Server Error", ex.getMessage(),req.getServletPath()),headers, status, req);
    }


    @ExceptionHandler(UnAutherizeException.class)
    public ResponseEntity<ErrorDto> UnauthorizeException(HttpServletRequest req, UnAutherizeException ex) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return handleContentNotAllowedException(ex, headers, status, req);


    }

    /** Customize the response for ContentNotAllowedException. */
    protected ResponseEntity<ErrorDto> handleContentNotAllowedException(UnAutherizeException ex, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {


        return handleExceptionInternal(ex, new ErrorDto(status.value(),"Unauthorized",ex.getMessage(),request.getServletPath()), headers, status, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ErrorDto> handleExceptionInternal(Exception ex, ErrorDto body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {

        return new ResponseEntity<ErrorDto>(body, headers, status);
    }
}