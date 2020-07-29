package com.silly.cli.jdbc.generator;

import com.silly.cli.jdbc.generator.config.DataSourceConfig;
import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.IDbQuery;
import com.silly.cli.jdbc.generator.config.StrategyConfig;
import com.silly.cli.jdbc.generator.config.po.FieldLogicDelete;
import com.silly.cli.jdbc.generator.config.po.TableField;
import com.silly.cli.jdbc.generator.config.po.TableInfo;
import com.silly.cli.jdbc.generator.config.rules.DbType;
import com.silly.cli.jdbc.generator.config.rules.IFiledType;
import com.silly.cli.jdbc.generator.config.rules.NameStrategy;
import com.silly.cli.jdbc.generator.engine.FreemarkerTemplateEngine;
import com.silly.cli.jdbc.generator.engine.ITemplateEngine;
import com.silly.cli.jdbc.generator.utils.AssertUtils;
import com.silly.cli.jdbc.generator.utils.CollectionUtils;
import com.silly.cli.jdbc.generator.utils.Constants;
import com.silly.cli.jdbc.generator.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 自动生成入口
 *
 * @Author: wei.wang7
 * @Date: 2020/7/8 22:52
 */
@Slf4j
public class AutoGenerator {

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;

    /**
     * 生成策略配置
     */
    private StrategyConfig strategyConfig;


    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    /**
     * 数据库查询
     */
    private IDbQuery dbQuery;

    /**
     * 数据库类型
     */
    private DbType dbType;

    /**
     * 执行入口
     */
    public void execute() {
        log.info("===============开始自动生成数据库实体文件=====================");
        //前置条件处理
        this.verify().handleDataSource();
        //获取目标数据库表
        List<TableInfo> tableInfos = getTableInfos();
        log.info("获取到目标数据库表个数:{}", tableInfos.size());
        for (TableInfo tableInfo : tableInfos) {
            log.info("{} -------- {}", tableInfo.getName(), tableInfo.getComment());
        }
        //生成文件
        ITemplateEngine templateEngine = new FreemarkerTemplateEngine();
        templateEngine.init(tableInfos, globalConfig).generate();
        log.info("===============自动生成数据库实体文件完成=====================");
    }

    /**
     * 处理数据源
     */
    private void handleDataSource() {
        this.dbType = this.dataSource.getDbType();
        Class<? extends IDbQuery> idbQuery = this.dbType.getDbQuery();
        //反射创建实体对象
        try {
            this.dbQuery = idbQuery.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("反射创建DbQuery对象失败", e);
        }
    }


    /**
     * 获取数据库所有表信息
     *
     * @return
     */
    private List<TableInfo> getTableInfos() {
        log.info("开始查询数据库中的所有表信息");
        boolean isInclude = CollectionUtils.isNotEmpty(this.strategyConfig.getIncludeTables());
        boolean isExclude = CollectionUtils.isNotEmpty(this.strategyConfig.getExcludeTables());
        if (isInclude && isExclude) {
            throw new RuntimeException("策略配置中,includeTables与excludeTables为互斥条件，只能配置一项!");
        }
        List<TableInfo> tableInfos = new ArrayList<>();
        try {
            log.info("当前数据库连接信息");
            log.info("-----url:{}", this.dataSource.getUrl());
            log.info("-----dbType:{}", this.dbType.getDesc());
            log.info("-----driverName:{}", this.dataSource.getDriverName());
            log.info("-----username:{}", this.dataSource.getUsername());
            log.info("-----password:{}", this.dataSource.getPassword());
            String tablesSql = this.dbQuery.tablesSql();
            log.info("开始查询数据库所有表信息,执行sql:{}", tablesSql);

            try (Connection conn = this.dataSource.getConn();
                 PreparedStatement ps = conn.prepareStatement(tablesSql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //获取数据表名
                    String tableName = rs.getString(dbQuery.tableName());
                    //获取注解
                    String tableComment = rs.getString(dbQuery.tableComment());
                    //判断是否需要生成视图
                    if (this.strategyConfig.isSkipView() && "VIEW".equalsIgnoreCase(tableComment)) {
                        continue;
                    }
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setName(tableName);
                    tableInfo.setComment(tableComment);
                    tableInfos.add(tableInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TableInfo> globalTableInfos = new ArrayList<>();
        if (isInclude) {
            String[] includeTables = this.strategyConfig.getIncludeTables();
            for (String includeTable : includeTables) {
                for (TableInfo tableInfo : tableInfos) {
                    if (tableInfo.getName().equalsIgnoreCase(includeTable)) {
                        globalTableInfos.add(tableInfo);
                    }
                }
            }
        } else if (isExclude) {
            String[] excludeTables = this.strategyConfig.getExcludeTables();
            for (String excludeTable : excludeTables) {
                Iterator<TableInfo> iterator = tableInfos.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().getName();
                    if (name.equalsIgnoreCase(excludeTable)) {
                        iterator.remove();
                    }
                }
                globalTableInfos.addAll(tableInfos);
            }
        } else {
            globalTableInfos.addAll(tableInfos);
        }
        //获取表字段信息
        globalTableInfos.forEach(k -> getTableFields(k));
        return processTableInfo(tableInfos);
    }

    /**
     * 处理表其他信息
     *
     * @param tableInfos
     * @return
     */
    private List<TableInfo> processTableInfo(List<TableInfo> tableInfos) {
        for (TableInfo tableInfo : tableInfos) {
            //转换实体名称
            tableInfo.setEntityName(NameStrategy.convertName(tableInfo.getName(), strategyConfig.getTableNameStrategy(), 2));
            //转换dao名称
            if (StringUtils.isBlank(globalConfig.getMapperClassNameFormat())) {
                tableInfo.setMapperName(String.format(Constants.MAPPER_CLASS_NAME_FORMAT, tableInfo.getEntityName()));
            } else {
                tableInfo.setMapperName(String.format(globalConfig.getMapperClassNameFormat(), tableInfo.getEntityName()));
            }
            //转换xml名称
            if (StringUtils.isBlank(globalConfig.getXmlClassNameFormat())) {
                tableInfo.setXmlName(String.format(Constants.XML_CLASS_NAME_FORMAT, tableInfo.getEntityName()));
            } else {
                tableInfo.setXmlName(String.format(globalConfig.getXmlClassNameFormat(), tableInfo.getEntityName()));
            }
            importPackages(tableInfo);
        }
        return tableInfos;
    }

    /**
     * 导入需要导入的包
     *
     * @param tableInfo
     */
    private void importPackages(TableInfo tableInfo) {
        //收集字段上需要导入的包
        List<TableField> fields = tableInfo.getFields();
        for (TableField field : fields) {
            IFiledType filedType = field.getFiledType();
            if (StringUtils.isNotBlank(filedType.getPkg())) {
                tableInfo.getImportPackages().add(filedType.getType());
            }
        }
        //开启Lombok注解,需要导入对应注解包
        if (globalConfig.isEntityLombokModel()) {
            tableInfo.getImportPackages().add(Data.class.getCanonicalName());
        }

    }

    /**
     * 获取表字段信息
     *
     * @param tableInfo
     */
    private void getTableFields(TableInfo tableInfo) {
        List<TableField> tableFields = new ArrayList<>();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            switch (dbType) {
                case MYSQL:
                    tableFieldsSql = String.format(tableFieldsSql, tableName);
                    break;
                default:
                    throw new RuntimeException("获取表字段信息,不支持的数据库类型");
            }
            try (Connection conn = this.dataSource.getConn();
                 PreparedStatement ps = conn.prepareStatement(tableFieldsSql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //列名
                    String fieldName = rs.getString(dbQuery.fieldName());
                    //排除字段
                    if (globalConfig.getExcludeFields().contains(fieldName)) {
                        continue;
                    }
                    TableField tableField = new TableField();
                    tableField.setName(fieldName);

                    //列字段
                    tableField.setComment(rs.getString(dbQuery.fieldComment()));
                    //列类型
                    String type = rs.getString(dbQuery.fieldType());
                    tableField.setType(type);
                    //字段名称
                    tableField.setFiledName(NameStrategy.convertName(fieldName, strategyConfig.getColumnNameStrategy(), 1));
                    //字段类型
                    tableField.setFiledType(this.dataSource.getTypeConvert().convertType(globalConfig, type));
                    //是否为主键
                    tableField.setKeyFlag(dbQuery.isKey(rs));
                    //主键是否自增
                    tableField.setKeyAutoIncrement(dbQuery.isKeyAutoIncrement(rs));
                    //判断是否存在逻辑删除
                    if (fieldName.equalsIgnoreCase(globalConfig.getLogicDeleteField())) {
                        tableInfo.setLogicDelete(true);
                        FieldLogicDelete fieldLogic = new FieldLogicDelete();
                        fieldLogic.setName(fieldName);
                        fieldLogic.setNotDeleteValue(globalConfig.getLogicNotDeleteValue());
                        fieldLogic.setDeleteValue(globalConfig.getLogicDeleteValue());
                        tableInfo.setFieldLogicDelete(fieldLogic);
                    }
                    tableFields.add(tableField);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("获取表字段信息,表名:" + tableName, ex);
        }
        tableInfo.setFields(tableFields);
    }

    /**
     * 校验参数
     */
    private AutoGenerator verify() {
        AssertUtils.notNull(this.dataSource, "数据库配置信息为空");
        this.dataSource.verify();
        if (this.strategyConfig == null) {
            this.strategyConfig = new StrategyConfig();
        }
        if (this.globalConfig == null) {
            this.globalConfig = new GlobalConfig();
        }
        if (StringUtils.isNotBlank(globalConfig.getLogicDeleteField())
                && (globalConfig.getLogicDeleteValue() == null || globalConfig.getLogicNotDeleteValue() == null)) {
            throw new RuntimeException("逻辑删除字段、逻辑删除值、逻辑未删除值三者必须共存赋值");
        }
        return this;
    }


    public void setDataSource(DataSourceConfig dataSource) {
        this.dataSource = dataSource;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public void setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }
}
