package com.catchu.chain.responsibility.controller;

import com.catchu.chain.responsibility.base.Permisson;
import com.catchu.chain.responsibility.base.VerifyType;
import com.catchu.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过职责链模式判断是否需要登录
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class NeedOrNoLoginController {

    @GetMapping("/testNone")
    @Permisson(verifyType = VerifyType.NONE)
    public Response listCourseOrder(){
        System.out.println("不需要登录");
        return new Response<>().ok();
    }

    @GetMapping("/testLogin")
    @Permisson(verifyType = VerifyType.LOGIN)
    public Response listCourseOrder(@RequestParam("name") String name){
        System.out.println("需要登录:"+name);
        return new Response<>().ok();
    }
}
