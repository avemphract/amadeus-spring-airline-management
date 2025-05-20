package com.katafrakt.airlinemanagement.configurations.aop;
import com.katafrakt.airlinemanagement.InitializationEvent;
import com.katafrakt.airlinemanagement.utilities.AuthenticationUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ServiceLogging {

    @Before("execution (* com.katafrakt.airlinemanagement.services..*(..))")
    public void logBefore(JoinPoint joinPoint){
        if (InitializationEvent.isInitializationCompleted()){
            Class<?> methodClass = joinPoint.getTarget().getClass();
            Logger log = LoggerFactory.getLogger(methodClass);

            var username = AuthenticationUtils.getAuthenticatedUsername();
            var methodName = joinPoint.getSignature().getName();
            log.info("[{}] -> {} fonksiyonunu çağırdı.", username, methodName);
        }
    }
}
