package com.catchu.interview;

import java.util.Arrays;

/**
 * @author junzhongliu
 * @date 2019/9/19 17:09
 */
public class TwoSumLessTarget {
    public static void main(String[] args) {
        int arr[]={34,23,1,24,75,33,54,8};
        int target = 60;
        System.out.println(twoSumLessTarget(arr,target));
    }

    public static int twoSumLessTarget(int arr[],int target){
        Arrays.sort(arr);
        int l=0,r=arr.length-1;
        int result=-1;
        while (l<r){
            if(arr[l]+arr[r]>=target){
                r--;
            }else{
                result=Math.max(result,arr[l]+arr[r]);
                l++;
            }
        }
        return result;
    }
}
