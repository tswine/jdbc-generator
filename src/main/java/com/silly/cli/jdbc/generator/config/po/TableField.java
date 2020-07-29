package com.silly.cli.jdbc.generator.config.po;

import com.silly.cli.jdbc.generator.config.rules.FiledType;
import com.silly.cli.jdbc.generator.config.rules.IFiledType;
import com.silly.cli.jdbc.generator.utils.StringUtils;
import lombok.Data;

/**
 * 数据库表字段信息
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:48
 */
@Data
public class TableField {

    /**
     * 是否主键
     */
    private boolean keyFlag;
    /**
     * 主键是否自增
     */
    private boolean keyAutoIncrement;
    /**
     * 数据库列字段
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 字段注解
     */
    private String comment;
    /**
     * 实体字段名称
     */
    private String filedName;
    /**
     * 实体字段类型
     */
    private IFiledType filedType;


    /**
     * 按JavaBean规则来生成get和set方法
     */
    public String getCapitalName() {
        if (filedName.length() <= 1) {
            return filedName.toUpperCase();
        }
        String setGetName = filedName;
        if (FiledType.BASE_BOOLEAN.getType().equalsIgnoreCase(filedType.getType())) {
            setGetName = StringUtils.removeIsPrefixIfBoolean(setGetName, Boolean.class);
        }
        // 第一个字母 小写、 第二个字母 大写 ，特殊处理
        String firstChar = setGetName.substring(0, 1);
        if (Character.isLowerCase(firstChar.toCharArray()[0])
                && Character.isUpperCase(setGetName.substring(1, 2).toCharArray()[0])) {
            return firstChar.toLowerCase() + setGetName.substring(1);
        }
        return firstChar.toUpperCase() + setGetName.substring(1);
    }

}
