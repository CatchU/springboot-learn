package com.catchu.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Objects;

/**
 * 全局方法调用日志，用于跟踪调用栈
 *
 * @author
 */
@Aspect
@Component(value = "globalLogAspect")
@Slf4j
public class GlobalLogAspect {

    @Pointcut("execution(public * com.catchu..*Storage.*(..)) " +
            "|| execution(public * com.catchu..*DAO.*(..)) " +
            "|| execution(public * com.catchu..*Mapper.*(..)) ")

    public void repositories() {

    }

    @Pointcut("execution(public * com.catchu..*Service.*(..)) " +
            "|| execution(public * com.catchu..*ServiceImpl.*(..)) " +
            "|| execution(public * com.catchu..*Consumer.*(..)) " +
            "|| execution(public * com.catchu..*Task.*(..)) ")
    public void services() {

    }

    @Pointcut("execution(public * com.catchu..*Controller.*(..)) " +
            "|| execution(public * com.catchu..*Feign.*(..)) " +
            "|| execution(public * com.catchu..*Proxy.*(..)) ")
    public void controllers() {

    }

    @Around("controllers() || services() || repositories()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        Long userId = GlobalRequestContext.getUserId();

        log.info("{}.{}({}) | uid={},duration={}",
                AopUtils.getTargetClass(joinPoint.getTarget()).getCanonicalName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()),
                Objects.isNull(userId) ? "none" : userId, end - begin);

        return proceed;
    }
}
