package com.catchu.component.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fansl
 * @Description: 消息工具类
 * @date 2019/2/26 17:43
 */
@Slf4j
public class RocketMQServiceUtil {
    /**
     * Transfer date string to timestamp
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Long getTimestampByDateString(String dateString) throws ParseException {
        Long time = null;
        if (StringUtils.isNotEmpty(dateString)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(dateString);
            time = date.getTime();
        }
        return time;
    }
}
