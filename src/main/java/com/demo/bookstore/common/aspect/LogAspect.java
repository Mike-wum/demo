package com.demo.bookstore.common.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LogAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.demo.bookstore..*.*Controller.*(..))")
    public void logController() {
    }

    @Around("logController()")
    public Object doControllerAround(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        try {
            log.info("Method {} >> args: {} ", method, JSON.toJSONString(pjp.getArgs()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object result = pjp.proceed();

        try {
            log.info("Method {} << result: {} ", method, JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @AfterThrowing(pointcut = "logController()", throwing = "e")
    public void doControllerAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Service Exception!", e);
    }
}
