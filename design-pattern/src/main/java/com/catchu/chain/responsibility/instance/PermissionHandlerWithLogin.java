package com.catchu.chain.responsibility.instance;

import com.catchu.chain.responsibility.base.AbstractPermissionHandler;
import com.catchu.chain.responsibility.base.VerifyType;

import javax.servlet.http.HttpServletRequest;

/**
 * @author junzhongliu
 * @date 2019/7/26 12:35
 */
public class PermissionHandlerWithLogin extends AbstractPermissionHandler {

    @Override
    public boolean hanlerChain(HttpServletRequest request, VerifyType verifyType, String[] vertifyValues) {
        if(verifyType == VerifyType.LOGIN){
            if("TEST".equals(request.getParameter("name"))){
                return true;
            }
        }
        return false;
    }
}
