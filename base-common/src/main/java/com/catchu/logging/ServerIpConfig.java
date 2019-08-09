package com.catchu.logging;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.PropertyDefiner;
import ch.qos.logback.core.status.Status;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取服务器IP
 */
@Slf4j
public class ServerIpConfig implements PropertyDefiner {
    @Override
    public String getPropertyValue() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.info("logback-ip:{}",ip);
            return ip;
        } catch (UnknownHostException e) {
            log.error("获取日志Ip异常", e);
        }
        return "127.0.0.1";
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void addStatus(Status status) {

    }

    @Override
    public void addInfo(String s) {

    }

    @Override
    public void addInfo(String s, Throwable throwable) {

    }

    @Override
    public void addWarn(String s) {

    }

    @Override
    public void addWarn(String s, Throwable throwable) {

    }

    @Override
    public void addError(String s) {

    }

    @Override
    public void addError(String s, Throwable throwable) {

    }
}
