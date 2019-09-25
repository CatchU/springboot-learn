package com.catchu.serializable;

public class SyncClass implements Runnable {
    static int i=0;
    //当前实例
    public  synchronized void test(){
        i++;
    }


    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            test();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1=new Thread(new SyncClass());
        Thread thread2=new Thread(new SyncClass());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }
}