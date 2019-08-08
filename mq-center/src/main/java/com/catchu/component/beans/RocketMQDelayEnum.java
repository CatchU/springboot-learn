package com.catchu.component.beans;

/**
 * 消息延时枚举
 * @author junzhongliu
 * @date 2019/8/8 16:48
 */
public enum RocketMQDelayEnum {

    ONE(1,"1s"),
    TWO(2,"5s"),
    THREE(3,"10s"),
    FOUR(4,"30s"),
    FIVE(5,"1m"),
    SIX(6,"2m"),
    SEVEN(7,"3m"),
    EIGHT(8,"4m"),
    NINE(9,"5m"),
    TEN(10,"6m"),
    ELEVEN(11,"7m"),
    TWELVE(12,"8m"),
    THIRTEEN(13,"9m"),
    FOURTEEN(14,"10m"),
    FIFTEEN(15,"20m"),
    SIXTEEN(16,"30m"),
    SEVENTEEN(17,"1h"),
    EIGHTEEN(18,"2h");

    private int id;

    private String desc;

    RocketMQDelayEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
