package com.project8.project8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.project8.project8.service.PostService.*(..))")
    public void logBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("[메서드 실행 @Before] {} - 파라미터: {}", methodName, Arrays.toString(args));
    }


    @Around("execution(* com.project8.project8.service.PostService.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("[메서드 실행 @Around] {} - 파라미터: {}", methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        log.info("[메서드 완료 @Around] {} - 실행 시간: {}ms, 결과: {}", methodName, duration, result);
        return result;
    }

    @AfterReturning(value = "execution(* com.project8.project8.service.PostService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("[메서드 완료 @AfterReturning] {} - 결과: {}", methodName, result);
    }

    @After("execution(* com.project8.project8.service.PostService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("[메서드 완료 @After] {}", methodName);

    }
}
