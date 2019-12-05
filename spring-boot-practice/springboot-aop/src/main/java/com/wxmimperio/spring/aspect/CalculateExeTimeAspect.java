package com.wxmimperio.spring.aspect;

import com.wxmimperio.spring.annotation.CalculateExeTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CalculateExeTimeAspect {

    @Pointcut("execution(* com.wxmimperio.spring..*(..))")
    private void myPointcut() {
    }

    @Around("myPointcut() && @annotation(calculateExeTime) && args(args,..)")
    public Object logExeTime(ProceedingJoinPoint joinPoint, String args, CalculateExeTime calculateExeTime) throws Throwable {
        long start = System.currentTimeMillis();
        // ProceedingJoinPoint 代表连接的的方法，proceed为执行该方法
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println(String.format("args = %s, cost = %s", args, (end - start)));
        return proceed;
    }
}
