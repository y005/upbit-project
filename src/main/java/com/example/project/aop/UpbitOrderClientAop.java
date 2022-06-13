package com.example.project.aop;

import com.example.project.dto.OrderResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UpbitOrderClientAop {
    @Around("@annotation(com.example.project.annotation.OrderErrorHandler)")
    public void report(ProceedingJoinPoint joinPoint) {
        OrderResult result = null;
        try {
            result = (OrderResult) joinPoint.proceed();
            if (result == null) {
                log.info("주문 실행 중 에러 발생");
            }
            else {
                log.info("주문 결과: {}", result);
            }
        }
        catch (Throwable e) {
            log.info("주문 실행 중 에러 발생");
        }
    }
}