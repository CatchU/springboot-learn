package com.catchu.exception.beans;

/**
 * @author junzhongliu
 * @date 2019/8/8 18:49
 */
public class ArgumentException extends RuntimeException {

    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }
}
