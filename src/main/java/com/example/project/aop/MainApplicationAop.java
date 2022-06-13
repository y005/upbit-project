package com.example.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MainApplicationAop {
    @Around("@annotation(com.example.project.annotation.ErrorHandler)")
    public void error(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("백그라운드 실행 중 에러 발생: {}", e.getMessage());
        }
    }
}
