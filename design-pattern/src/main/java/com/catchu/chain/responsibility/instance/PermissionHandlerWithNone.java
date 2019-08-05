package com.catchu.chain.responsibility.instance;

import com.catchu.chain.responsibility.base.AbstractPermissionHandler;
import com.catchu.chain.responsibility.base.VerifyType;
import com.catchu.chain.responsibility.instance.PermissionFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author junzhongliu
 * @date 2019/7/26 12:31
 */
public class PermissionHandlerWithNone extends AbstractPermissionHandler {

    @Override
    public boolean hanlerChain(HttpServletRequest request, VerifyType verifyType, String[] vertifyValues) {
        if(verifyType == VerifyType.NONE){
            return true;
        }else{
            setNextHandler(PermissionFactory.getPermissionHandlerWithLogin());
            return getNextHandler().hanlerChain(request,verifyType,vertifyValues);
        }
    }
}
