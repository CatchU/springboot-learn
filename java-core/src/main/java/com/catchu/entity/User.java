package com.catchu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author junzhongliu
 * @date 2019/8/5 12:51
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;
}
