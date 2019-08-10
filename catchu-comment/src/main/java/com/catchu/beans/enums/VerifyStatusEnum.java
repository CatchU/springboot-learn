package com.catchu.beans.enums;

import lombok.Getter;

public enum VerifyStatusEnum {

    Prepare(1, "待审核"),

    @Deprecated
    Denied(2, "不通过"),

    Ignore(3, "忽略"),

    Pass(4, "通过");

    @Getter
    private int type;

    @Getter
    private String desc;

    VerifyStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
