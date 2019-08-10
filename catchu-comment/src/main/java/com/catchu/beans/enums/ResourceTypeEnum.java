package com.catchu.beans.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 资源类型枚举
 */
public enum ResourceTypeEnum {

    Book(1, "图书留言"),

    Article(2, "文章留言"),

    Dictation(3, "听写报告"),

    BookFamous(4, "名家名作"),

    BookDetective(5, "侦探社"),

    BookFunny(6, "奇趣乐园"),

    BookIsland(7, "童话岛"),

    BookStory(8, "精选故事"),

    BookSpecial(2, "专栏文章库"),

    BookExplore(10, "探索世界"),

    BookList(11, "推荐书单"),

    ReaderActivity(12, "朗读者活动"),
    ;

    @Getter
    private int id;

    @Getter
    private String name;

    ResourceTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ResourceTypeEnum getById(int id) {
        return Arrays.stream(ResourceTypeEnum.values()).filter(ct -> ct.getId() == id).findFirst().orElse(null);
    }
}
