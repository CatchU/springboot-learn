package com.catchu.serializable;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author junzhongliu
 * @date 2019/8/27 19:28
 */
public class TimeTest {

    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.YEAR,1);
        System.out.println(instance.get(Calendar.YEAR));

        instance.add(Calendar.MONTH,1);
        System.out.println(instance.get(Calendar.MONTH));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusYears(-1);
        System.out.println(localDateTime.getYear());

        Calendar cal1=Calendar.getInstance();
        System.out.println(cal1.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal1.get(Calendar.DAY_OF_YEAR));
        System.out.println(cal1.get(Calendar.DATE));

    }
}
