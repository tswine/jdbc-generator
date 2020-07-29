package com.silly.cli.jdbc.generator.config;

import com.silly.cli.jdbc.generator.config.rules.IFiledType;

/**
 * 类型转换器
 *
 * @Author: wei.wang7
 * @Date: 2020/7/18 14:54
 */
public interface ITypeConvert {

    /**
     * 转换类型
     *
     * @param globalConfig 全局配置
     * @param columnType   列类型
     * @return 字段类型
     */
    IFiledType convertType(GlobalConfig globalConfig, String columnType);
}
