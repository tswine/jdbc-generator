package com.silly.cli.jdbc.generator.config;

import com.silly.cli.jdbc.generator.config.rules.NameStrategy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 策略配置项
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 18:39
 */
@Data
public class StrategyConfig {

    /**
     * 是否跳过视图
     */
    private boolean skipView = true;

    /**
     * 数据库表映射到实体命名策略
     */
    private NameStrategy tableNameStrategy = NameStrategy.UNDERLINE_TO_CAMEL;

    /**
     * 数据库表字段映射到实体字段命名策略
     */
    private NameStrategy columnNameStrategy = NameStrategy.UNDERLINE_TO_CAMEL;


    /**
     * 包含的表名，优先级高于excludeTables
     */
    @Setter(AccessLevel.NONE)
    private String[] includeTables = null;

    /**
     * 排除的表名
     */
    @Setter(AccessLevel.NONE)
    private String[] excludeTables = null;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean entitySerialVersionUID = true;

    /**
     * 实体是否生成列名常量
     * 例如: public static final String COLUMN_ID  ="id";
     */
    private boolean entityColumnConstant = false;

    /**
     * 设置包含表名
     *
     * @param includeTables
     */
    public void setIncludeTables(String... includeTables) {
        this.includeTables = includeTables;
    }

    /**
     * 设置排除表名
     *
     * @param excludeTables
     */
    public void setExcludeTables(String... excludeTables) {
        this.excludeTables = excludeTables;
    }

    public void verify() {
    }
}
