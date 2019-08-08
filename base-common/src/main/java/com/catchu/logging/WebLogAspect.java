package com.catchu.logging;

import com.catchu.exception.beans.ArgumentException;
import com.catchu.exception.beans.BadRequestException;
import com.catchu.exception.beans.ForbiddenException;
import com.catchu.exception.beans.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.isNull;

/**
 * 实现Web层的日志切面
 *
 * @author
 */
@Aspect
@Component(value = "webLogAspect")
@Slf4j
public class WebLogAspect {

    /**
     * 定义一个切入点. 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 任意包名 ~ 第三个 * 代表任意方法. ~ 第四个 * 定义在web包或者子包 ~
     * 第五个 * 任意方法 ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.catchu..*Controller.*(..))")
    public void webLog() {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        GlobalRequestContext.clear();

        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long total = end - GlobalRequestContext.getApiBegin();

        Long userId = GlobalRequestContext.getUserId();

        log.info("{}.{}({}) | MAGIC_OB,url={},uid={},total={},db={},service={}",
                AopUtils.getTargetClass(joinPoint.getTarget()).getCanonicalName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()),
                GlobalRequestContext.getURL(),
                isNull(userId) ? "none" : userId, total, GlobalRequestContext.getDbCostTime(),
                total - GlobalRequestContext.getDbCostTime());

        return proceed;
    }

    @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    public void doRecoveryActions(Throwable ex) {

        if (isKoalaBusinessException(ex)) {
            if (log.isDebugEnabled()) {
                log.warn("business exception: ", ex);
            }
            return;
        }

        long now = System.currentTimeMillis();
        log.error("url={},uid={},total={},db={},service={}",
                GlobalRequestContext.getURL(),
                GlobalRequestContext.getUserId(),
                now - GlobalRequestContext.getApiBegin(),
                GlobalRequestContext.getDbCostTime(),
                now - GlobalRequestContext.getApiBegin() - GlobalRequestContext.getDbCostTime(),
                ex);
    }

    private boolean isKoalaBusinessException(Throwable ex) {

        return ex instanceof BadRequestException
                || ex instanceof ArgumentException
                || ex instanceof UnauthorizedException
                || ex instanceof ForbiddenException;
    }

}
