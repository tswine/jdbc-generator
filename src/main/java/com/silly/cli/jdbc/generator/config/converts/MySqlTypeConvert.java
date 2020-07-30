package com.silly.cli.jdbc.generator.config.converts;

import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.ITypeConvert;
import com.silly.cli.jdbc.generator.config.rules.FiledType;
import com.silly.cli.jdbc.generator.config.rules.IFiledType;

/**
 * MySQL 类型转换器
 *
 * @Author: wei.wang7
 * @Date: 2020/7/18 14:56
 */
public class MySqlTypeConvert implements ITypeConvert {
    @Override
    public IFiledType convertType(GlobalConfig globalConfig, String columnType) {
        String column = columnType.toLowerCase();
        if (column.contains("char")) {
            return FiledType.STRING;
        } else if (column.contains("bigint")) {
            return FiledType.LONG;
        } else if (column.contains("tinyint(1)")) {
            return FiledType.BOOLEAN;
        } else if (column.contains("int")) {
            return FiledType.INTEGER;
        } else if (column.contains("text")) {
            return FiledType.STRING;
        } else if (column.contains("bit")) {
            return FiledType.BOOLEAN;
        } else if (column.contains("decimal")) {
            return FiledType.BIG_DECIMAL;
        } else if (column.contains("clob")) {
            return FiledType.CLOB;
        } else if (column.contains("blob")) {
            return FiledType.BLOB;
        } else if (column.contains("binary")) {
            return FiledType.BYTE_ARRAY;
        } else if (column.contains("float")) {
            return FiledType.FLOAT;
        } else if (column.contains("double")) {
            return FiledType.DOUBLE;
        } else if (column.contains("json") || column.contains("enum")) {
            return FiledType.STRING;
        }else  if (column.contains("date")  || column.contains("time") || column.contains("year")){
            switch (globalConfig.getDateType()){
                case ONLY_DATE:
                    return FiledType.DATE;
                case SQL_PACK:
                    switch (column) {
                        case "date":
                            return FiledType.DATE_SQL;
                        case "time":
                            return FiledType.TIME;
                        case "year":
                            return FiledType.DATE_SQL;
                        default:
                            return FiledType.TIMESTAMP;
                    }
                case TIME_PACK:
                    switch (column) {
                        case "date":
                            return FiledType.LOCAL_DATE;
                        case "time":
                            return FiledType.LOCAL_TIME;
                        case "year":
                            return FiledType.YEAR;
                        default:
                            return FiledType.LOCAL_DATE_TIME;
                    }
            }
        }
        return FiledType.STRING;
    }
}
