package com.silly.cli.jdbc.generator.utils;

/**
 * 断言工具类
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 22:38
 */
public class AssertUtils {

    /**
     * 断言obj不为null
     *
     * @param obj
     * @param message
     */
    public static void notNull(Object obj, String message) {
        isTrue(obj != null, message);
    }

    /**
     * 断言value不为empty
     *
     * @param value
     * @param message
     */
    public static void notEmpty(String value, String message) {
        isTrue(StringUtils.isNotBlank(value), message);
    }


    /**
     * 断言boolean是否为true
     *
     * @param expression 校验boolean值
     * @param message    提示消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new RuntimeException(message);
        }
    }
}
