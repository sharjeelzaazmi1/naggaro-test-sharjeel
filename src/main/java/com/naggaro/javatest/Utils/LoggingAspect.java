package com.naggaro.javatest.Utils;


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
            log.info(p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " START");
            Object[] signatureArgs = p.getArgs();


            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {

                if (signatureArgs[0] != null) {
                    log.info("\nRequest object: \n" + mapper.writeValueAsString(signatureArgs[0]));
                }
            } catch (JsonProcessingException e) {
            }
    }

    @AfterReturning(value = ("within(com.naggaro.javatest.controllers..*)"),
            returning = "returnValue")
    public void endpointAfterReturning(JoinPoint p, Object returnValue) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                log.info("\nResponse object: \n" + mapper.writeValueAsString(returnValue));
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
            log.info(p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " END");

    }




}