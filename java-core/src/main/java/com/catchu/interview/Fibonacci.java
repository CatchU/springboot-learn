package com.catchu.interview;

/**
 * 使用递归和非递归计算斐波那契数列,非递归速度非常快
 * @author junzhongliu
 * @date 2019/9/4 14:01
 */
public class Fibonacci {

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        int recursion = recursion(44);
        System.out.println("递归计算结果:"+recursion);
        long end1 = System.currentTimeMillis();
        System.out.println("递归耗时:"+(end1-start1));

        long start2 = System.currentTimeMillis();
        int nonRecursion = nonRecursion(44);
        System.out.println("非递归计算结果:"+nonRecursion);
        long end2 = System.currentTimeMillis();
        System.out.println("非递归耗时:"+(end2-start2));

    }

    public static int recursion(int month){
        if(month<1){
            return -1;
        }
        if(month==1 || month==2){
            return 1;
        }else{
            return recursion(month-1)+recursion(month-2);
        }
    }

    public static int nonRecursion(int month){
        if(month<1){
            return -1;
        }
        int thisMonth = 1;
        int result = 1;
        int temp;
        for(int i=1;i<=month;i++){
            if(i<=2){
                result=1;
            }else{
                temp=result;
                result+=thisMonth;
                thisMonth=temp;
            }
        }
        return result;
    }
}
