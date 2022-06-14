package com.example.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UpbitNotificationClientAop {
    @Around("@annotation(com.example.project.annotation.AssetsErrorHandler)")
    public double error(ProceedingJoinPoint joinPoint) {
        try {
            return (double) joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("코인 정보 확인 중 에러 발생: {}", e.getMessage());
            return 0d;
        }
    }
}