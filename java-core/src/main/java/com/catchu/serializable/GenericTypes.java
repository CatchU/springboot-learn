package com.catchu.serializable;

import java.util.List;

/**
 * 泛型产出问题会导致此类编译不通过，因为编译后都是List了，没有List<String>和List<Integer>了
 */
public class GenericTypes {

//    public static void method(List<String> list) {
//        System.out.println("invoke method(List<String> list)");
//    }

    public static void method(List<Integer> list) {  
        System.out.println("invoke method(List<Integer> list)");  
    }  
}  