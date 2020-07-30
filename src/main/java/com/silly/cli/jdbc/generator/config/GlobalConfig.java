package com.silly.cli.jdbc.generator.config;

import com.silly.cli.jdbc.generator.config.rules.DateType;
import com.silly.cli.jdbc.generator.config.rules.NameStrategy;
import com.silly.cli.jdbc.generator.utils.Constants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * 全局配置
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:20
 */
@Data
public class GlobalConfig {

    /**
     * 生成文件输出目录
     */
    private String outputDir;

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;
    /**
     * 是否打开输出目录
     */
    private boolean open = true;
    /**
     * 开发人员
     */
    private String author;

    /**
     * 开启 BaseResultMap,该值修改无效
     */
    private boolean baseResultMap = true;
    /**
     * 父包名
     */
    private String parentPacketName = Constants.DEFAULT_PARENT_PACKAGE;
    /**
     * 实体包名
     */
    private String entityPacketName = Constants.ENTITY_PACKAGE_NAME;
    /**
     * mapper层包名
     */
    private String mapperPacketName = Constants.MAPPER_PACKAGE_NAME;
    /**
     * mapper文件命名格式
     */
    private String mapperClassNameFormat = Constants.MAPPER_CLASS_NAME_FORMAT;
    /**
     * xml层包名
     */
    private String xmlPacketName = Constants.XML_PACKAGE_NAME;
    /**
     * xml文件命名格式
     */
    private String xmlClassNameFormat = Constants.XML_CLASS_NAME_FORMAT;
    /**
     * 实体字段是否生成常量
     */
    private boolean entityColumnConstant = false;
    /**
     * 是否采用lombok实体注解
     */
    private boolean entityLombokModel = true;
    /**
     * 是否覆盖文件
     */
    private boolean override = false;
    /**
     * 逻辑删除字段
     */
    private String logicDeleteField;
    /**
     * 逻辑删除值
     */
    private Integer logicDeleteValue;
    /**
     * 逻辑未删除值
     */
    private Integer logicNotDeleteValue;
    /**
     * 排除字段
     */
    private List<String> excludeFields;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.ONLY_DATE;

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

}
