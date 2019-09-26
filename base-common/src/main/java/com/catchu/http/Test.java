package com.catchu.http;

import com.alibaba.fastjson.JSONObject;
import com.catchu.http.builders.HttpParamsBuilder;
import com.catchu.http.enums.ContentTypeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * @author junzhongliu
 * @date 2019/9/25 20:30
 */
public class Test {

    public static void main(String[] args) {
        //普通get请求
        //System.out.println(doGet());

        //普通post请求
        //System.out.println(doPost());

        //测试将接收到的数据转成List列表
        String s = doPost2();
        JSONObject jsonObject = JSONObject.parseObject(s);
        String content = jsonObject.getString("content");
        String list = JSONObject.parseObject(content).getString("list");
        List<CourseUserTaskRecordVO> courseUserTaskRecordVOS = HttpParamsBuilder.arrayParamsBuilder(list, CourseUserTaskRecordVO.class);
        System.out.println(JSONObject.toJSONString(courseUserTaskRecordVOS));

        //测试post请求，json格式，request body是Collection类型
        //System.out.println(doPost3());
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

    public static String doPost(){
        try {
            String url = "http://localhost:9090/course/web/internal/courseUserTaskRecord/getRecordList";
            Map<String,String> params = new HashMap<>();
            params.put("taskId","");
            params.put("lessonId","1");
            params.put("finishTypeList", "");
            params.put("pageNum","0");
            params.put("pageSize","15");
            return HttpClientAssistant.doPost(url, HttpParamsBuilder.mapParamsBuilder(params), "utf-8", ContentTypeEnum.APPOLICATION_JSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost2(){
        try {
            String url = "http://localhost:9090/course/web/internal/courseUserTaskRecord/getRecordList";
            Map<String,Object> params = new HashMap<>();
            params.put("taskId","");
            params.put("lessonId",1);
            params.put("finishTypeList", "");
            params.put("pageNum",0);
            params.put("pageSize",15);
            return HttpClientAssistant.doPost(url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost3(){
        try {
            String url = "http://localhost:8081/users/server/user/listUser";
            Set<Long> userIds = Sets.newHashSet(227L,229L,229L,230L);
            return HttpClientAssistant.doPost(url, userIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

@Data
@Accessors(chain = true)
class CourseUserTaskRecordVO implements Serializable {

    private Long id;

    private Long userId;

    private Long lessonId;

    private Long taskId;

    private Long elementId;

    private String recordDetail;

    private Date finishTime;

    private Long operatorId;

    private Integer level;

    private String correctContentUrl;

    private Integer finishType;

    private Integer modifyStatus;

    private Long lastOperatorId;

    private Date taskEndTime;

    private String taskName;

    private Integer taskType;

    private Date modifyTime;

    /**
     * 所属课文名称
     */
    private String elementName;

    /**
     * 评语类型（1-文字类型评语，2-语音类型评语）
     */
    private Integer commentType;

    /**
     * 老师批改任务列表
     */
    private List<String> correctContentUrlList;

    /**
     * 最后操作人名称
     */
    private String lastOperatorName;

    /**
     * 用户手机号
     */
    private String tel;

    private String operatorName;

    private String userName;
}

