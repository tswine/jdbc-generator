package com.silly.cli.jdbc.generator.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 22:50
 */
public class CollectionUtils {

    /**
     * 校验集合是否为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 校验集合是否不为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 校验数组是否为空
     *
     * @param objs
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] objs) {
        return (objs == null) || (objs.length <= 0);
    }

    /**
     * 校验数组是否不为空
     *
     * @param objs
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty(T[] objs) {
        return !isEmpty(objs);
    }

    /**
     * 判断Map是否为空
     *
     * @param map 入参
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断Map是否不为空
     *
     * @param map 入参
     * @return boolean
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
