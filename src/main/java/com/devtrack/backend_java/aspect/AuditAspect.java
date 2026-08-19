package com.devtrack.backend_java.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.devtrack.backend_java.entity.User;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("serviceMethods()")
    public Object logSeviceCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        String username = getCurrentUsername();

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            log.info("AUDIT | user={} | method={} | args={} | duration={}ms | status=SUCCESS",
                    username, methodName, Arrays.toString(args), duration);

            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;

            log.warn("AUDIT | user={} | method={} | args={} | duration={}ms | status=FAILED | error={}",
                    username, methodName, Arrays.toString(args), duration, ex.getMessage());

            throw ex; // re-throw — don't swallow the real exception
        }
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User user) {
            return user.getEmail();
        }

        return "anonymous";
    }

    @Pointcut(
              "execution(* com.devtrack.backend_java..*.*(..))&& " +
          "!within(com.devtrack.backend_java.security..*) && " +
          "!within(com.devtrack.backend_java.config..*)")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeSeviceCall(JoinPoint joinPoint) throws Throwable {
         String methodName = "";
        try {
            methodName = getMethodName(joinPoint);  
            log.info("Calling "+ methodName);
        } catch (Throwable ex) {
            log.warn("Calling "+ methodName);
            throw ex; // re-throw — don't swallow the real exception
        }
    }

    @After("serviceMethods()")
    public void logAfterSeviceCall(JoinPoint joinPoint) throws Throwable {
        String methodName = "";
        try {
            methodName =  getMethodName(joinPoint);
            log.info("End "+ methodName);
        } catch (Throwable ex) {
            log.warn("End "+ methodName);
            throw ex; // re-throw — don't swallow the real exception
        }
    }

    public String getMethodName(JoinPoint joinPoint){
        return joinPoint.getSignature().toShortString();  
    }

}
