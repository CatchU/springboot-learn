package com.catchu.beans.enums;

import lombok.Getter;

public enum ContentTypeEnum {

    Text(1, "文本"),

    Image(2, "图片"),

    Voice(3, "语音");

    @Getter
    private int type;

    @Getter
    private String desc;

    ContentTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
