package com.example.backend.logs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method input and output for service methods.
 * Auteur: Youssef Elouafi
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Logs the input arguments of the method being executed.
     * This is executed before the method is called.
     *
     * @param joinPoint Provides reflective access to the method being executed.
     */
    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && execution(* com.example.backend.service.ProductService.*(..))")
    public void logMethodInput(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    /**
     * Logs the output (result) of the method after it has completed successfully.
     * This is executed after the method successfully returns a result.
     *
     * @param joinPoint Provides reflective access to the method being executed.
     * @param result The result returned by the method.
     */
    @AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.RequestMapping) && execution(* com.example.backend.service.ProductService.*(..))", returning = "result")
    public void logMethodOutput(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }

    /**
     * Logs an exception if the method throws an exception during execution.
     * This is executed after the method throws an exception.
     *
     * @param joinPoint Provides reflective access to the method being executed.
     * @param exception The exception thrown by the method.
     */
    @AfterThrowing(pointcut = "@annotation(org.springframework.web.bind.annotation.RequestMapping) && execution(* com.example.backend.service.ProductService.*(..))", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature(), exception.getMessage());
    }
}