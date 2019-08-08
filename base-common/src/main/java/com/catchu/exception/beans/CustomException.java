package com.catchu.exception.beans;

/**
 * 自定义异常
 * 可以返回自定义错误码和错误信息的异常
 */
public class CustomException extends RuntimeException  {

    private String msg;

    private int code;

    /**
     * Exception 异常体中内容，也可以返给前端某些需要的信息
     */
    private Object content;

    public CustomException(String msg){
        super(msg);
        this.msg = msg;
    }

    public CustomException(String msg, Throwable e){
        super(msg,e);
        this.msg = msg;
        this.content = msg;
    }

    public CustomException(String msg, int code){
        super(msg);
        this.msg = msg;
        this.code = code;
        this.content = msg;
    }

    public CustomException(String msg, int code, Object content){
        super(msg);
        this.msg = msg;
        this.code = code;
        this.content = content;
    }

    public CustomException(String msg, int code, Throwable e){
        super(msg,e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

