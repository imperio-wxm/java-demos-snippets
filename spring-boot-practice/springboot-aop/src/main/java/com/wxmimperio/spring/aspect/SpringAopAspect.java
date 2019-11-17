package com.wxmimperio.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 定义切面
@Aspect
@Component
public class SpringAopAspect {

    // 定义切点
    @Pointcut("execution(* com.wxmimperio.spring..*.*(..))")
    private void methodPointCut() {
    }

    // 定义在apollo包和所有子包里的任意类的任意方法的执行
    //@AfterReturning("execution(* com.wxmimperio.spring..*.*(..))")
    @AfterReturning("methodPointCut()")
    public void logService(JoinPoint joinPoint) {
        System.out.println("getArgs=" + Arrays.asList(joinPoint.getArgs()));
        System.out.println("getKind=" + joinPoint.getKind());
        System.out.println("getSignature=" + joinPoint.getSignature());
        System.out.println("getSourceLocation=" + joinPoint.getSourceLocation());
        System.out.println("getStaticPart=" + joinPoint.getStaticPart());
        System.out.println("getTarget=" + joinPoint.getTarget());
        System.out.println("getThis=" + joinPoint.getThis());
        System.out.println("getClass=" + joinPoint.getClass());
    }
}
