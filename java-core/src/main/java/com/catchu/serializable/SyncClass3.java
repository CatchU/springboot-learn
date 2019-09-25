package com.catchu.serializable;

public class SyncClass3 implements Runnable {
    static SyncClass instance=new SyncClass();
    static int i=0;
    public  void test(){
        i++;
    }


    @Override
    public void run() {
//给定的实例
//        synchronized (instance){
//            for (int j=0;j<1000000;j++){
//                test();
//            }
//        }
//当前实例
//        synchronized (this){
//            for (int j=0;j<1000000;j++){
//                test();
//            }
//        }
//当前类
        synchronized (SyncClass.class){
            for (int j=0;j<1000000;j++){
                test();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SyncClass insatance=new SyncClass();
        Thread thread1=new Thread(insatance);
        Thread thread2=new Thread(insatance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }
}