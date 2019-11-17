package com.wxmimperio.spring;

import com.wxmimperio.spring.service.SpringAopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Aspect：即切面，切面一般定义为一个 Java 类， 每个切面侧重于特定的跨领域功能，比如，事务管理或者日志打印等。
 * Joinpoint：即连接点，程序执行的某个点，比如方法执行。构造函数调用或者字段赋值等。在 Spring AOP 中，连接点只会有 方法调用 (Method execution)。
 * Advice：即通知，在连接点要的代码。
 * Pointcut：即切点，一个匹配连接点的正则表达式。当一个连接点匹配到切点时，一个关联到这个切点的特定的 通知 (Advice) 会被执行。
 * Weaving：即编织，负责将切面和目标对象链接，以创建通知对象，在 Spring AOP 中没有这个东西。
 */

@SpringBootApplication
// 开启aop
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationAop {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(ApplicationAop.class, args);
        SpringAopService springAopService = applicationContext.getBean(SpringAopService.class);
        springAopService.aopTransfer("test");
    }
}
