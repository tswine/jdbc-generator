package com.silly.cli.jdbc.generator.config.rules;

/**
 * 字段类型
 *
 * @Author: wei.wang7
 * @Date: 2020/7/18 14:48
 */
public interface IFiledType {

    /**
     * 获取字段类型
     *
     * @return
     */
    String getType();

    /**
     * 获取字段类型对应包名
     */
    String getPkg();
}
