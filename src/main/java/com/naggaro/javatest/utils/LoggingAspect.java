package com.naggaro.javatest.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Before("within(com.naggaro.javatest.controllers..*)")
    public void endpointBefore(JoinPoint p) {
            log.error("{} {} {}", p.getTarget().getClass().getSimpleName(), p.getSignature().getName(),
                "START");

            Object[] signatureArgs = p.getArgs();


            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {

                if (signatureArgs[0] != null) {

                    log.info("Request object:{} " , mapper.writeValueAsString(signatureArgs[0]));
                }
            } catch (JsonProcessingException e) {
                log.error("Error while json process of request: " ,e);
            }
    }

    @AfterReturning(value = ("within(com.naggaro.javatest.controllers..*)"),
            returning = "returnValue")
    public void endpointAfterReturning(JoinPoint p, Object returnValue) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                log.info("Response object:  {}" , mapper.writeValueAsString(returnValue));
            } catch (JsonProcessingException e) {
                log.error("Error while processing json response: {}" ,e.getMessage(),e);
            }
            log.error("{} {} {}", p.getTarget().getClass().getSimpleName(), p.getSignature().getName(),
                "END");

    }




}