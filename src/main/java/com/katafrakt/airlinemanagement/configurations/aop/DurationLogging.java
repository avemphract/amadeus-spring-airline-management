package com.katafrakt.airlinemanagement.configurations.aop;

import com.katafrakt.airlinemanagement.utilities.AuthenticationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DurationLogging {


    @Around("@annotation(com.katafrakt.airlinemanagement.configurations.aop.DurationLoggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Class<?> methodClass = joinPoint.getTarget().getClass();
        Logger log = LoggerFactory.getLogger(methodClass);

        var username = AuthenticationUtils.getAuthenticatedUsername();
        var methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();

        var processResult = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("[{}]->{} fonksiyonu {} ms sürdü.", username, methodName, duration);
        return processResult;
    }
}
