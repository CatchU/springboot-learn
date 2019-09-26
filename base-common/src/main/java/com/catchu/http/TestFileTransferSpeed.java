package com.catchu.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 测试文件传输速度
 * @author junzhongliu
 * @date 2019/9/26 11:41
 */
public class TestFileTransferSpeed {

    public static void main(String[] args) throws Exception {
        long startTime1 = System.currentTimeMillis();
        traditionTest();
        long endTime1 = System.currentTimeMillis();
        System.out.println("消耗时间1:" + (endTime1 - startTime1));

        long startTime2 = System.currentTimeMillis();
        fileUtilTest();
        long endTime2 = System.currentTimeMillis();
        System.out.println("消耗时间2:" + (endTime2 - startTime2));

        long startTime3 = System.currentTimeMillis();
        nioTest();
        long endTime3 = System.currentTimeMillis();
        System.out.println("消耗时间3:" + (endTime3 - startTime3));
    }

    /**
     * 最传统的方式   40M文件 byte字节1024时平均220ms  byte字节8192时平均时长70ms
     * @throws Exception
     */
    public static void traditionTest() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("\\home\\www\\source.mp4"));
        FileOutputStream outputStream = new FileOutputStream(new File("\\home\\www\\target.mp4"));
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
        }
        outputStream.close();
        inputStream.close();
    }

    /**
     * 使用java8或者commons IO的FileUtils或者guava包下的copy方法  40M文件 80ms左右
     * @throws Exception
     */
    public static void fileUtilTest() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("\\home\\www\\source.mp4"));
        File out = new File("\\home\\www\\target.mp4");
        Files.copy(inputStream,out.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * FileChannel方式  这种是综合效率最高的一种读写方式 40M文件 60ms左右 用到了nio相关知识
     */
    public static void nioTest() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("\\home\\www\\source.mp4"));
        FileOutputStream outputStream = new FileOutputStream(new File("\\home\\www\\target.mp4"));

        FileChannel in = inputStream.getChannel();
        FileChannel out = outputStream.getChannel();
        in.transferTo(0,in.size(),out);
    }
}
