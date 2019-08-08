package com.catchu.exception.beans;

/**
 * @author junzhongliu
 * @date 2019/8/8 18:50
 */
public class ForbiddenException extends RuntimeException{

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}