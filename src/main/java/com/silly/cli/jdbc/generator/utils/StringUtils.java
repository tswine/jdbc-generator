package com.silly.cli.jdbc.generator.utils;

import java.io.File;

/**
 * 字符串工具类
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 19:56
 */
public class StringUtils implements StringPool {

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalFirst(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return EMPTY;
    }


    /**
     * 判断字符串是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int l = cs.length();
        if (l > 0) {
            for (int i = 0; i < l; i++) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 去除boolean类型is开头的字符串
     *
     * @param propertyName 字段名
     * @param propertyType 字段类型
     */
    public static String removeIsPrefixIfBoolean(String propertyName, Class<?> propertyType) {
        if ((boolean.class == propertyType || Boolean.class == propertyType) && propertyName.startsWith(IS)) {
            String property = propertyName.replaceFirst(IS, EMPTY);
            if (isBlank(property)) {
                return propertyName;
            } else {
                String firstCharToLowerStr = firstCharToLower(property);
                return property.equals(firstCharToLowerStr) ? propertyName : firstCharToLowerStr;
            }
        }
        return propertyName;
    }


    /**
     * 第一个首字母小写，之后字符大小写的不变
     * <p>StringUtils.firstCharToLower( "UserService" )     = userService</p>
     * <p>StringUtils.firstCharToLower( "UserServiceImpl" ) = userServiceImpl</p>
     *
     * @param rawString 需要处理的字符串
     * @return ignore
     */
    public static String firstCharToLower(String rawString) {
        return prefixToLower(rawString, 1);
    }

    /**
     * 前n个首字母小写,之后字符大小写的不变
     *
     * @param rawString 需要处理的字符串
     * @param index     多少个字符(从左至右)
     * @return ignore
     */
    public static String prefixToLower(String rawString, int index) {
        String beforeChar = rawString.substring(0, index).toLowerCase();
        String afterChar = rawString.substring(index);
        return beforeChar + afterChar;
    }

    /**
     * 将点转换为文件目录
     *
     * @return
     */
    public static String pointToSeparator(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.replace(".", File.separator);
        }
        return str;
    }
}
