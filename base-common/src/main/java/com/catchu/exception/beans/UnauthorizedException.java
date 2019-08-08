package com.catchu.exception.beans;

/**
 * @author junzhongliu
 * @date 2019/8/8 19:04
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Invalid User Identification");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
