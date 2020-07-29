package com.silly.cli.jdbc.generator.config.po;

import lombok.Data;

/**
 * 逻辑删除字段
 *
 * @Author: wei.wang7
 * @Date: 2020/7/28 21:36
 */
@Data
public class FieldLogicDelete {

    /**
     * 逻辑删除字段名
     */
    private String name;
    /**
     * 逻辑未删除值
     */
    private Integer notDeleteValue;
    /**
     * 逻辑删除值
     */
    private Integer deleteValue;


}
