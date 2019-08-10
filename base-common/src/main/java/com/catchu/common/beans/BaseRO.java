package com.catchu.common.beans;

import java.util.UUID;

/**
 * 所有接口入参RO均继承此基类
 */
public class BaseRO {

    private String uuid;

    public String getUuid() {
        this.uuid = (null == uuid || "".equals(uuid)) ? UUID.randomUUID().toString().replaceAll("-", "") : uuid;
        return this.uuid;
    }
}
