package com.catchu.exception.beans;

/**
 * @author junzhongliu
 * @date 2019/8/8 18:49
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}