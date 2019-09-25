package com.catchu.http;

import com.catchu.http.beans.HttpClientBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junzhongliu
 * @date 2019/9/25 20:30
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(doGet());
    }

    public static String doGet(){
        try {
            String url = "http://localhost:9090/course/web/wx/courseOrder/getTelByUserId/5";
            Map<String,String> params = new HashMap<>();
            return HttpClientAssistant.doGet(url, params, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
