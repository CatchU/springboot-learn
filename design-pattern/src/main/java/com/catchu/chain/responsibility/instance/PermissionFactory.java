package com.catchu.chain.responsibility.instance;

/**
 * @author junzhongliu
 * @date 2019/7/26 12:33
 */
public class PermissionFactory {

    public static PermissionHandlerWithNone getPermissionHandlerWithNone(){
        return new PermissionHandlerWithNone();
    }

    public static PermissionHandlerWithLogin getPermissionHandlerWithLogin(){
        return new PermissionHandlerWithLogin();
    }
}
