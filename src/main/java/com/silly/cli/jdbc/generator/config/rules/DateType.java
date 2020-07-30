package com.silly.cli.jdbc.generator.config.rules;

/**
 * 数据库时间类型到实体类时间类型对应策略
 *
 * @Author: wei.wang7
 * @Date: 2020/7/30 21:05
 */
public enum DateType {
    /**
     * 只使用 java.util.date 代替
     */
    ONLY_DATE,
    /**
     * 使用 java.sql 包下的
     */
    SQL_PACK,
    /**
     * 使用 java.time 包下的
     * <p>java8 新的时间类型</p>
     */
    TIME_PACK
}
