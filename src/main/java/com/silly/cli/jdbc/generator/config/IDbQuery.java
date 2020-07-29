package com.silly.cli.jdbc.generator.config;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:54
 */
public interface IDbQuery {

    /**
     * 表信息查询 SQL
     */
    String tablesSql();


    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();


    /**
     * 表名称
     */
    String tableName();


    /**
     * 表注释
     */
    String tableComment();

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    String fieldComment();

    /**
     * 主键字段
     */
    String fieldKey();

    /**
     * 是否为主键
     *
     * @param rs
     * @return
     */
    boolean isKey(ResultSet rs) throws SQLException;

    /**
     * 主键是否自增
     *
     * @param rs
     * @return
     */
    boolean isKeyAutoIncrement(ResultSet rs) throws SQLException;
}
