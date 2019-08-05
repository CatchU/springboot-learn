package com.catchu.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应数据
 * @author junzhongliu
 * @date 2019/8/5 13:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    /**
     * 响应结果
     */
    private boolean success;

    /**
     * 响应内容
     */
    private T content;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 状态码
     */
    private int code;

    public Response ok() {
        this.success = true;
        this.message = "ok";
        this.code = 200;
        return this;
    }

    public Response<T> ok(T t) {
        this.content = t;
        return this.ok();
    }

    public Response error(String message) {
        this.success = false;
        this.message = message;
        this.code = 500;
        return this;
    }
}
