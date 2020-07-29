package com.silly.cli.jdbc.generator.config.rules;

import com.silly.cli.jdbc.generator.config.IDbQuery;
import com.silly.cli.jdbc.generator.config.ITypeConvert;
import com.silly.cli.jdbc.generator.config.converts.MySqlTypeConvert;
import com.silly.cli.jdbc.generator.config.querys.MySqlQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 数据库类型
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum DbType {
    MYSQL("mysql", "MySQL数据库", "com.mysql.jdbc.Driver", MySqlQuery.class, MySqlTypeConvert.class);
    /**
     * 数据库名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;

    /**
     * 驱动名称
     */
    private String driverName;
    /**
     * 数据库操作执行器
     */
    private Class<? extends IDbQuery> dbQuery;

    /**
     * 类型转换器
     */
    private Class<? extends ITypeConvert> typeConvert;


}
