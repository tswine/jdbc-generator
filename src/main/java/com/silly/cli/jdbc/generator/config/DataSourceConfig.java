package com.silly.cli.jdbc.generator.config;

import com.silly.cli.jdbc.generator.config.rules.DbType;
import com.silly.cli.jdbc.generator.utils.AssertUtils;
import com.silly.cli.jdbc.generator.utils.StringUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author: wei.wang7
 * @Date: 2020/7/8 22:54
 */
@Data
public class DataSourceConfig {

    /**
     * 连接url
     */
    private String url;
    /**
     * 驱动名称
     */
    @Getter(AccessLevel.NONE)
    private String driverName;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 数据库类型
     */
    @Getter(AccessLevel.NONE)
    private DbType dbType;

    public void verify() {
        AssertUtils.notEmpty(this.url, "数据库连接url不能为空");
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public Connection getConn() {
        Connection conn;
        try {
            Class.forName(getDriverName());
            conn = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public DbType getDbType() {
        if (null == this.dbType) {
            //用户没有设置DbType,则从连接url中读取
            this.dbType = this.getDbType(this.url);

        }
        return this.dbType;
    }


    public String getDriverName() {
        if (StringUtils.isBlank(this.driverName)) {
            this.driverName = getDbType().getDriverName();
        }
        return this.driverName;
    }

    /**
     * 获取字段类型转换器
     *
     * @return
     */
    public ITypeConvert getTypeConvert() {
        DbType dbType = getDbType();
        Class<? extends ITypeConvert> typeConvert = dbType.getTypeConvert();
        try {
            return typeConvert.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建类型转换器实体对象异常", e);
        }
    }

    /**
     * 判断数据库类型
     *
     * @param str 用于寻找特征的字符串，可以是 driverName 或小写后的 url
     * @return 类型枚举值，如果没找到，则返回 null
     */
    private DbType getDbType(String str) {
        //TODO 需要优化，存在bug
        if (str.contains(DbType.MYSQL.getName())) {
            this.dbType = DbType.MYSQL;
            return DbType.MYSQL;
        }
        throw new RuntimeException(String.format("不支持的数据库类型:%s", str));
    }
}
