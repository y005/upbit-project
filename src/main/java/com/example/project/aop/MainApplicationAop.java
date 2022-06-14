package com.example.project.aop;

import com.example.project.application.ApplicationTerminator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class MainApplicationAop {
    private ApplicationTerminator applicationTerminator;

    @Around("@annotation(com.example.project.annotation.ErrorHandler)")
    public void error(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("백그라운드 실행 중 에러 발생: {}", e.getMessage());
            log.info("프로그램을 종료합니다. 종료시각 - {}", LocalDateTime.now());
            applicationTerminator.exit();
        }
    }
}
