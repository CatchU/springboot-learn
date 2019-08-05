package com.catchu.chain.responsibility.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author junzhongliu
 * @date 2019/7/26 11:56
 */
public abstract class AbstractPermissionHandler {

    private AbstractPermissionHandler nextHandler;

    public abstract boolean hanlerChain(HttpServletRequest request, VerifyType verifyType, String[] vertifyValues);

    public AbstractPermissionHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(AbstractPermissionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
