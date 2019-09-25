package com.catchu.serializable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author junzhongliu
 * @date 2019/8/31 16:31
 */
public class DeadLock {

    public static void main(String[] args) {
        String str1 = "锁1";
        String str2 = "锁2";
        DeadLockThread lockThread1 = new DeadLockThread(str1, str2);
        lockThread1.setName("线程1");
        lockThread1.start();

        DeadLockThread lockThread2 = new DeadLockThread(str2, str1);
        lockThread2.setName("线程2");
        lockThread2.start();
    }
}

class DeadLockThread extends Thread{
    private String str1;

    private String str2;

    public DeadLockThread(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    @Override
    public void run(){
        synchronized (str1){
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"已获取锁，准备拿取"+str2);
                synchronized (str2){
                    System.out.println(Thread.currentThread().getName() + "猜猜能不能进来");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
