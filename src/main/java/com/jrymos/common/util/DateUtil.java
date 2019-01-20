package com.jrymos.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

import static org.apache.commons.lang3.math.NumberUtils.toInt;

/**
 * Created by fly
 */
public class DateUtil {

    /**
     * 将 "yyyyMMddHHssmm" 格式字符串转换为 date
     *
     * @param str "yyyyMMddHHssmm" 格式字符串
     * @return date
     */
    public static Date yyyyMMddHHssmm2Date(String str) {
        if (NumberUtils.isDigits(str)) {
            String year = StringUtils.substring(str, 0, 4);
            String month = StringUtils.substring(str, 4, 6);
            String day = StringUtils.substring(str, 6, 8);
            String hour = StringUtils.substring(str, 8, 10);
            String minute = StringUtils.substring(str, 10, 12);
            String second = StringUtils.substring(str, 12, 14);
            return new Date(toInt(year, 0) - 1900, toInt(month, 1) - 1, toInt(day, 1),
                    toInt(hour, 0), toInt(minute, 0), toInt(second, 0));
        }
        return null;
    }

    /**
     * 将 "yyyy-MM-dd HH：ss：mm" 或 "yyyy/MM/dd HH：ss：mm" 等 标准时间格式字符串转换为 date
     *
     * @param str "yyyy-MM-dd HH:ss:mm" 等格式字符串
     * @return date
     */
    public static Date standardToDate(String str) {
        if (NumberUtils.isDigits(str)) {
            String year = StringUtils.substring(str, 0, 4);
            String month = StringUtils.substring(str, 5, 7);
            String day = StringUtils.substring(str, 8, 10);
            String hour = StringUtils.substring(str, 11, 13);
            String minute = StringUtils.substring(str, 14, 16);
            String second = StringUtils.substring(str, 17, 19);
            return new Date(toInt(year, 0) - 1900, toInt(month, 1) - 1, toInt(day, 1),
                    toInt(hour, 0), toInt(minute, 0), toInt(second, 0));
        }
        return null;
    }
}
