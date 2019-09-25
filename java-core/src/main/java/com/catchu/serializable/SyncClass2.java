package com.catchu.serializable;

public class SyncClass2 implements Runnable {
    static int i=0;
    //锁的是当前类
    public static synchronized void test(){
        i++;
    }


    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            test();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1=new Thread(new SyncClass2());
        Thread thread2=new Thread(new SyncClass2());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }
}