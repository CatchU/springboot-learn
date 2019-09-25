package com.catchu.serializable;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用反射，在List<Integer>中插入一个String类型数据,使用反射可以绕过类型检查
 * @author junzhongliu
 * @date 2019/8/27 16:10
 */
public class InsertElement {

    public static void main(String[] args) throws Exception{
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Class<ArrayList> listClass = ArrayList.class;
        //Class<?> listClass = Class.forName("java.util.ArrayList");
        //Class<? extends List> listClass = list.getClass();
        Method method = listClass.getMethod("add", Object.class);
        method.invoke(list,"测试");
        System.out.println(JSONObject.toJSONString(list));
    }
}
