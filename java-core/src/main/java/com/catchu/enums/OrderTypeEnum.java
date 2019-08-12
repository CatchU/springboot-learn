package com.catchu.enums;

import lombok.Getter;

/**
 * @author junzhongliu
 * @date 2019/7/2 15:32
 */
@Getter
public enum OrderTypeEnum {

    /**
     * 订单类型
     */
    YOUZAN(1,"有赞订单","E"),
    XIAOETONG(2,"小鹅通订单","X"),
    OFFLINE(3,"线下订单","L"),
    KOALA_WECHAT(4,"考拉微信小程序","W");

    private int id;

    private String value;

    /**
     * 生成订单号的字母代号
     */
    private String letter;

    OrderTypeEnum(int id, String value, String letter){
        this.id = id;
        this.value = value;
        this.letter = letter;
    }


    public static OrderTypeEnum findById(int id) {
        for (OrderTypeEnum type : OrderTypeEnum.values()) {
            if (id == type.getId()) {
                return type;
            }
        }
        return OFFLINE;
    }

    public static String findChar(int id) {
        for (OrderTypeEnum type : OrderTypeEnum.values()) {
            if (id == type.getId()) {
                return type.getLetter();
            }
        }
        //默认有赞
        return OrderTypeEnum.YOUZAN.getLetter();
    }
}
