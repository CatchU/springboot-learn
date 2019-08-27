package com.catchu.serializable;

import lombok.Data;
import java.io.*;

/**
 * 测试序列化
 * @author junzhongliu
 * @date 2019/8/26 16:16
 */
public class TestSerialize {

    public static void write(){
        User user = new User();
        user.setAge(1);
        user.setName("张三");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\soft\\11.txt"))){
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\soft\\11.txt"))){
            User user1 = (User)ois.readObject();
            System.out.println(user1);
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //write();
        read();
    }
}

@Data
class User implements Serializable{

    private static final long serialVersionUID = 1907449602450640009L;

    private int age;

    private String name;

//    private int id;
//
//    private String desc;
}
