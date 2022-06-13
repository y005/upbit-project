package com.example.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SlackWebhookClientAop {
    @Around("@annotation(com.example.project.annotation.SlackErrorHandler)")
    public void error(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("슬랙 메세지 전송 중 에러 발생: {}", e.getMessage());
        }
    }
}
