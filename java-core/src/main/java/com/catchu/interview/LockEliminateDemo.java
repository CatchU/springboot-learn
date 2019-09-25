package com.catchu.interview;

/**
 * 锁消除示例
 * @author junzhongliu
 * @date 2019/9/20 19:25
 */
public class LockEliminateDemo {

    public static void test(){
        Object obj = new Object();
        /**
         * obj这个对象属于test方法内部  并不会被其它线程访问到  所以在JIT编译阶段会消除锁
         */
        synchronized (obj){
            System.out.println("test");
        }
    }
}
