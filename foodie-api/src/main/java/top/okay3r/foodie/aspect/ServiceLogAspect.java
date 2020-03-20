package top.okay3r.foodie.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 记录service执行时间
 */

/**
 * AOP通知：
 * 1. 前置通知：在方法调用之前执行
 * 2. 后置通知：在方法正常调用之后执行
 * 3. 环绕通知：在方法调用之前和之后，都分别可以执行的通知
 * 4. 异常通知：如果在方法调用过程中发生异常，则通知
 * 5. 最终通知：在方法调用之后执行
 */

/**
 * 切面表达式：
 * execution 代表所要执行的表达式主体
 * 第一处 * 代表方法返回类型 *代表所有类型
 * 第二处 包名代表aop监控的类所在的包
 * 第三处 .. 代表该包以及其子包下的所有类方法
 * 第四处 * 代表类名，*代表所有类
 * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
 */
@Component
@Aspect
public class ServiceLogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("execution(* top.okay3r.foodie.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) {
        //获取开始时间
        long start = System.currentTimeMillis();

        Object result = null;
        try {
            //调用目标方法（service）
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        String s = joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName();

        //获取结束时间
        long end = System.currentTimeMillis();

        //计算出使用的时间
        long takeTime = end - start;

        if (takeTime > 3000) {
            LOGGER.error("Service 执行时间过长 [" + takeTime + "ms] 位于 " + s);
        } else if (takeTime > 2000) {
            LOGGER.warn("Service 执行时间稍长 [" + takeTime + "ms] 位于 " + s);
        } else {
            LOGGER.info("Service 执行完毕 [" + takeTime + "ms] 位于 " + s);
        }

        return result;
    }
}
