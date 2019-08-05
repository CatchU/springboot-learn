package com.catchu.java8.optional;

import com.catchu.entity.User;

import java.util.Optional;

/**
 * @author junzhongliu
 * @date 2019/8/5 12:51
 */
public class OptionalTest {
    public static void main(String[] args) {
        User user = null;
        User user1 = Optional.ofNullable(user).orElse(null);
        System.out.println(user1);

        user = new User().setId(1L).setName("test");
        User user2 = Optional.ofNullable(user).orElse(null);
        System.out.println(user2);

        if(Optional.ofNullable(user).isPresent()){
            System.out.println("user的值不为空");
        }
    }
}
