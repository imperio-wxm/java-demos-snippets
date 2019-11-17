package com.wxmimperio.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 定义切面，在此类中定义切点、通知等方法
@Aspect
@Component
public class SpringAopAspect {

    // 定义切点，用来告诉 Spring AOP 如何匹配连接点
    // 包含的指示器如下：
    // execution
    // within
    // this 和 target
    // args
    // @target
    // @annotation
    @Pointcut("execution(* com.wxmimperio.spring..*.*(..))")
    private void methodPointCut() {
    }

    // 定义在apollo包和所有子包里的任意类的任意方法的执行
    // Caused by: java.lang.IllegalArgumentException: warning no match for this type name:
    // 此错误表明：没有找到切入的地方，如果是不同的package需要执行包的全路径

    // 定义通知 Advice
    //@AfterReturning("execution(* com.wxmimperio.spring..*.*(..))")
    // 通知的类型：
    // Around
    // Before 用来执行在方法调用之前的操作，如果 Before Advice 在执行的过程中抛出异常的话，那么连接点的方法就不会被执行
    // After
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
