package com.silly.cli.jdbc.generator.config.querys;

import com.silly.cli.jdbc.generator.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL数据库查询实现
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:55
 */
public class MySqlQuery extends AbstractDbQuery {
    @Override
    public String tablesSql() {
        return "show table status";
    }

    @Override
    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }

    @Override
    public String tableName() {
        return "NAME";
    }

    @Override
    public String tableComment() {
        return "COMMENT";
    }

    @Override
    public String fieldName() {
        return "FIELD";
    }

    @Override
    public String fieldType() {
        return "TYPE";
    }

    @Override
    public String fieldComment() {
        return "COMMENT";
    }

    @Override
    public String fieldKey() {
        return "KEY";
    }

    @Override
    public boolean isKey(ResultSet rs) throws SQLException {
        String str = rs.getString(fieldKey());
        if (StringUtils.isNotBlank(str) && str.equalsIgnoreCase("PRI")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isKeyAutoIncrement(ResultSet rs) throws SQLException {
        String str = rs.getString("extra");
        if (StringUtils.isNotBlank(str) && str.equalsIgnoreCase("auto_increment")) {
            return true;
        }
        return false;
    }
}
