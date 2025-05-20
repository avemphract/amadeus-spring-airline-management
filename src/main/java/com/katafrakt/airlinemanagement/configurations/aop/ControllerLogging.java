package com.katafrakt.airlinemanagement.configurations.aop;

import com.katafrakt.airlinemanagement.InitializationEvent;
import com.katafrakt.airlinemanagement.utilities.AuthenticationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogging {

    @Pointcut("within(com.katafrakt.airlinemanagement.controllers..*)")
    public void controllerLayer() {}

    @Pointcut("!@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void controllerNotGetLater() {}

    @Order(1)
    @Before("controllerLayer()")
    public void logBefore(JoinPoint joinPoint){
        if (InitializationEvent.isInitializationCompleted()){
            Class<?> methodClass = joinPoint.getTarget().getClass();
            Logger log = LoggerFactory.getLogger(methodClass);

            var username = AuthenticationUtils.getAuthenticatedUsername();
            var methodName = joinPoint.getSignature().getName();
            log.info("-----------------------");
            log.info("[{}] -> {} action'u tetiklendi.", username, methodName);
        }
    }

    @After("controllerLayer()")
    public void logAfter(JoinPoint joinPoint){
        if (InitializationEvent.isInitializationCompleted()){
            Class<?> methodClass = joinPoint.getTarget().getClass();
            Logger log = LoggerFactory.getLogger(methodClass);

            var username = AuthenticationUtils.getAuthenticatedUsername();
            var methodName = joinPoint.getSignature().getName();
            log.info("[{}] -> {} action'u sonuçlandı.", username, methodName);
        }
    }

    @Order(2)
    @Before("controllerLayer() && controllerNotGetLater()")
    public void logImportantBefore(JoinPoint joinPoint){
        if (InitializationEvent.isInitializationCompleted()){
            Class<?> methodClass = joinPoint.getTarget().getClass();
            Logger log = LoggerFactory.getLogger(methodClass);

            var username = AuthenticationUtils.getAuthenticatedUsername();
            log.info("[{}] -> İşlem veri değişikliğine sebep olabilir.", username);
        }
    }


}
