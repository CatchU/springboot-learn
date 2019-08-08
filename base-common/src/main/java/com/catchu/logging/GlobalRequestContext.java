package com.catchu.logging;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class GlobalRequestContext {

    private static final ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    private static final ThreadLocal<AtomicLong> DB_COST_TIME = ThreadLocal.withInitial(AtomicLong::new);

    private static final ThreadLocal<Long> API_BEGIN = new ThreadLocal<>();

    private static final ThreadLocal<String> URL = new ThreadLocal<>();

    private static final ThreadLocal<String> DEVICE_ID = new ThreadLocal<>();

    /**
     * 微信小程序需要绑定的参数
     */
    private static final ThreadLocal<Map<String, Object>> WECHAT_PARAM = ThreadLocal.withInitial(Maps::newHashMap);


    public static String getURL() {
        return URL.get();
    }

    public static void setURL(String url) {
        URL.remove();
        URL.set(url);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setUserId(long userId) {
        USER_ID.remove();
        USER_ID.set(userId);
    }

    public static String getRequestId() {
        return REQUEST_ID.get();
    }

    public static void setRequestId(String requestId) {
        REQUEST_ID.remove();
        REQUEST_ID.set(requestId);
    }

    static Long getDbCostTime() {
        return DB_COST_TIME.get().get();
    }

    static void setDbCostTime(long dbCostTime) {
        DB_COST_TIME.get().addAndGet(dbCostTime);
    }

    static void clear() {
        DB_COST_TIME.remove();
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN.get();
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN.remove();
        ACCESS_TOKEN.set(accessToken);
    }

    static Long getApiBegin() {
        return API_BEGIN.get();
    }

    static void setApiBegin(Long apiBegin) {
        API_BEGIN.remove();
        API_BEGIN.set(apiBegin);
    }

    public static Object getWechatParam(String name) {
        return WECHAT_PARAM.get().get(name);
    }

    public static Object setWechatParam(String name, Object value) {
        return WECHAT_PARAM.get().put(name, value);
    }

    public static String getDeviceId(){
        return DEVICE_ID.get();
    }

    public static void setDeviceId(String deviceId){
        DEVICE_ID.remove();
        DEVICE_ID.set(deviceId);
    }
}
