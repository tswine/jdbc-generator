package com.silly.cli.jdbc.generator;


import com.silly.cli.jdbc.generator.config.DataSourceConfig;
import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.rules.DateType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AutoGeneratorTest {

    @Test
    public void execute() {
        AutoGenerator autoGenerator = new AutoGenerator();

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://192.168.47.201:3306/log?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        autoGenerator.setDataSource(dataSourceConfig);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("E:\\workspace\\cli\\jdbc-generator\\src\\main\\java");
        globalConfig.setAuthor("silly");
        globalConfig.setParentPacketName("temp");
        globalConfig.setEntityColumnConstant(true);
        globalConfig.setEntityLombokModel(true);
        globalConfig.setOverride(true);
        globalConfig.setLogicDeleteField("yn");
        globalConfig.setLogicNotDeleteValue(1);
        globalConfig.setLogicDeleteValue(-1);
        globalConfig.setExcludeFields(Arrays.asList("ex1"));
        globalConfig.setDateType(DateType.TIME_PACK);
        autoGenerator.setGlobalConfig(globalConfig);

        autoGenerator.execute();
    }
}