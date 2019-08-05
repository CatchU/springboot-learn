package com.catchu.chain.responsibility.interceptor;

import com.catchu.chain.responsibility.base.Permisson;
import com.catchu.chain.responsibility.base.VerifyType;
import com.catchu.chain.responsibility.instance.PermissionFactory;
import com.catchu.chain.responsibility.instance.PermissionHandlerWithNone;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author junzhongliu
 * @date 2019/7/26 11:47
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            Method method = hm.getMethod();
            if(method.isAnnotationPresent(Permisson.class)){
                Permisson annotation = method.getAnnotation(Permisson.class);
                VerifyType verifyType = annotation.verifyType();
                String[] values = annotation.verifyValues();

                PermissionHandlerWithNone handlerWithNone = PermissionFactory.getPermissionHandlerWithNone();
                return handlerWithNone.hanlerChain(request,verifyType,values);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
