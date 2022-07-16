package com.ydw.utils;

/**
 * @author ydw
 * @create 2022-07-12 20:49
 */
public class StringUtils {
    public static boolean isEmpty(String str){
        return str==null||str.equals("");
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
