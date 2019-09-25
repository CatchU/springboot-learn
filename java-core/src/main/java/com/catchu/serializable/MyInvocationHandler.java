package com.catchu.serializable;

import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Date;

/**
 * 测试jdk动态代理
 * @author junzhongliu
 * @date 2019/8/27 17:06
 */
public class MyInvocationHandler implements InvocationHandler {

    // 代理的目标对象
    private Object target;

    public MyInvocationHandler(Object target){
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+"--------------begin");

        Object result = method.invoke(target, args);
        System.out.println(method.getName()+"---------------------end");
        System.out.println("result="+result);
        return result;
    }

    public Object getInstance(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),this);
    }

    public static void main(String[] args) {
        UserService target = new UserServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(target);
        UserService proxy = (UserService)handler.getInstance();
        proxy.add();
    }
}
