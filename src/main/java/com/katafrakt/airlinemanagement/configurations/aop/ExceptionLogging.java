package com.katafrakt.airlinemanagement.configurations.aop;

import com.katafrakt.airlinemanagement.utilities.AuthenticationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLogging {

    @Around("execution (* com.katafrakt.airlinemanagement.services..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            return joinPoint.proceed();
        } catch (Exception e) {

            Class<?> methodClass = joinPoint.getTarget().getClass();
            Logger log = LoggerFactory.getLogger(methodClass);

            var username = AuthenticationUtils.getAuthenticatedUsername();
            var methodName = joinPoint.getSignature().getName();


            log.info("[{}] -> {} fonksiyonunda exception fırlatıldı. Exception: {}", username, methodName, e.toString());
            throw e;
        }
    }
}
