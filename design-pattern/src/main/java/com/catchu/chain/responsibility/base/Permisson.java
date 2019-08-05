package com.catchu.chain.responsibility.base;

import java.lang.annotation.*;

/**
 * @author junzhongliu
 * @date 2019/7/26 11:40
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permisson {

    VerifyType verifyType() default VerifyType.LOGIN;

    String[] verifyValues() default "";
}
