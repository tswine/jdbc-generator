# jdbc-generator 自动根据数据库表生成对应的实体对象及数据库操作层
### 
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 开发环境
- jdk:Java8
- 开发工具：IDEA
- 依赖管理：Maven

## 引入
- git clone  https://github.com/tswine/jdbc-generator
- mvn clean compile package install -DskipTests


## 使用样例
``` java
@Test
public void execute() {
    AutoGenerator autoGenerator = new AutoGenerator();

    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setDbType(DbType.MYSQL);
    dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
    dataSourceConfig.setUrl("jdbc:mysql://192.168.47.201:3306/log?useUnicode=true&useSSL=false&characterEncoding=utf8");
    dataSourceConfig.setUsername("root");
    dataSourceConfig.setPassword("123456");
    autoGenerator.setDataSource(dataSourceConfig);

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setOutputDir("E:\\workspace\\cli\\jdbc-generator\\src\\test\\java");
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
```
