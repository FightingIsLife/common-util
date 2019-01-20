package com.jrymos.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/30 22:40
 * @Description:
 */
public class StringTransUtils {

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderline(String para) {
        if (StringUtils.isEmpty(para)) {
            return null;
        }
        char[] chars = para.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        if (Character.isUpperCase(chars[0])) {
            stringBuilder.append(Character.toLowerCase(chars[0]));
        } else {
            stringBuilder.append(chars[0]);
        }
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                stringBuilder.append("_").append(Character.toLowerCase(chars[i]));
            }else {
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }

    public static String firstUppercase(String word){
        if (StringUtils.isNotEmpty(word)){
            char[] chars = word.toCharArray();
            chars[0] = Character.isUpperCase(chars[0]) ? chars[0] : Character.toUpperCase(chars[0]);
            return new String(chars);
        }
        return word;
    }

    public static void main(String[] args) {
        System.out.println(humpToUnderline("ProjectHello"));
        System.out.println(humpToUnderline("ProjectHEllo"));
        System.out.println(humpToUnderline("projectHEllO"));
    }
}
