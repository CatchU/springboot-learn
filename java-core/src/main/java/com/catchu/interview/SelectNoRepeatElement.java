package com.catchu.interview;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * 挑选集合中不重复元素
 * @author junzhongliu
 * @date 2019/9/4 11:48
 */
public class SelectNoRepeatElement {

    public static void main(String[] args) {
        Integer arr[] = {1,1,2,2,3,4,4,5,5,5};
        List<Integer> handler = handler(arr);
        System.out.println(JSONObject.toJSONString(handler));
    }

    public static List<Integer> handler(Integer[] arr){
        List<Integer> result = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        for(Integer key : arr){
            if(map.get(key) == null){
                map.put(key,1);
            }else{
                map.put(key,map.get(key)+1);
            }
        }

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            if(entry.getValue()<=1){
                result.add(entry.getKey());
            }
        }
        //遍历map
//        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
//            System.out.println(entry.getKey()+"-------"+ entry.getValue());
//        }
//
//        System.out.println("++++++++++++++++++++++++++");
//        Set<Integer> integers = map.keySet();
//        for(Integer i : integers){
//            System.out.println(i);
//        }
//        Collection<Integer> values = map.values();
//        for(Integer i: values){
//            System.out.println(i);
//        }
//
//        System.out.println("++++++++++++++++++++++");
//
//        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<Integer, Integer> next = iterator.next();
//            System.out.println(next.getKey()+"--------"+next.getValue());
//        }
//        System.out.println("++++++++++++++++++++++");
//
//        map.forEach((k,v) -> {
//            System.out.println(k+"---------"+v);
//        });
//        System.out.println("end");
        return result;
    }
}
