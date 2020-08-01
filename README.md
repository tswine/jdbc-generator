# jdbc-generator 自动根据数据库表生成对应的实体及Mapper代码
### 
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 开发环境
- jdk:Java8
- 开发工具：IDEA
- 依赖管理：Maven

## 引入
- git clone  https://github.com/tswine/jdbc-generator
- mvn clean compile package install -DskipTests
- use
  ```
  <dependency>
    <groupId>com.silly.cli</groupId>
    <artifactId>jdbc-generator</artifactId>
    <version>last-version</version>
  </dependency>
  ```


## 参数配置
### AutoGenerator 应用入口
|  名称   | 类型  |  描述 | 是否必须| 默认值 |
|  ----  | ----  | ----  | ----  | ----  |
| dataSource  | DataSourceConfig | 配置数据库相关信息实体对象 |是 | |  
| globalConfig  | GlobalConfig | 定制化全局配置实体对象 | 是 | |

--- 
### DataSourceConfig 数据源参数配置
|  名称   | 类型  |  描述 | 是否必须| 默认值 |
|  ----  | ----  | ----  | ----  | ----  |
| url  | String | 数据库ur |是 | |  
| driverName  | String | 驱动名称 | 否 | |
| username  | String | 用户名 | 是 | |
| password  | String | 密码 | 是 | |
| dbType  | DbType | 数据库类型 | 否 | |

--- 
### GlobalConfig 定制化全局配置
|  名称   | 类型  |  描述 | 是否必须| 默认值 |
|  ----  | ----  | ----  | ----  | ----  |
| outputDir  | String | 生成文件输出目录 |是 | |  
| override  | boolean | 是否覆盖已有文件 | 否 | false |
| open  | boolean | 是否打开输出目录 | 否 | true |
| author  | String | 开发人员 | 否 | |
| parentPacketName  | String | 父包名 |是 |com.xxx |
| entityPacketName  | String | 实体包名 | 是 | entity |
| mapperPacketName  | String | mapper层包名 |是 |mapper |
| mapperClassNameFormat  | String | mapper文件命名格式 | 是 | %sMapper |
| xmlPacketName  | String | xml层包名 | 是 |xml |
| xmlClassNameFormat  | String | xml文件命名格式 | 是 | %sMapper |
| entityColumnConstant  | boolean | 实体字段是否生成常量 | 是 |false |
| entityLombokModel  | boolean | 是否采用lombok实体注解 | 是 |true |
| logicDeleteField  | String | 逻辑删除字段 | 否 | |
| logicDeleteValue  | Integer | 逻辑删除值 | 否 | |
| logicNotDeleteValue  | Integer | 逻辑未删除值 | 否 | |
| excludeFields  | Integer | 逻辑未删除值 | 否 | |
| dateType  | DateType  | 时间类型对应策略 | 是 |ONLY_DATE |
| skipView  | boolean | 是否跳过视图 | 是 | true |
| tableNameStrategy  | NameStrategy | 数据库表映射到实体命名策略 | 是 |UNDERLINE_TO_CAMEL |
| columnNameStrategy  | NameStrategy | 数据库表字段映射到实体字段命名策略 | 是 |UNDERLINE_TO_CAMEL |
| includeTables  | String[] | 包含的表名，优先级高于excludeTables | 否 | |
| excludeTables  | String[]  | 排除的表名 | 否 | |
| entitySerialVersionUID  | boolean | 实体是否生成 serialVersionUID | 是 |true |

### 输出样例
#### 数据库表(log_demo)结构
```
CREATE TABLE `log_demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yn` int(11) DEFAULT NULL COMMENT '是否有效 1：有效 -1：无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志demo表'
```
## 测试代码样例
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

#### 输出实体(LogDemo.java)文件样例
```java
package temp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 日志demo表
 *
 * @Author silly
 * @Date 2020-8-1 13:00:12
 */
@Data
public class LogDemo implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 数据库静态常量字段列
     */
    public static final String FIELD_ID = "id";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_CREATE_TIME = "createTime";
    public static final String FIELD_YN = "yn";

    /**
     * 主键ID，自增
     */
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 是否有效 1：有效 -1：无效
     */
    private Integer yn;

}
```
#### 输出Mapper(LogDemoMapper.java) Java文件样例
```java
package temp.mapper;

import temp.entity.LogDemo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志demo表 mapper层
 *
 * @Author: wei.wang7
 * @Date: 2020/7/26 17:09
 */
@Mapper
public interface LogDemoMapper {

    /**
     * 根据主键获取实体对象
     *
     * @param id 主键ID，自增
     * @return 获取的实体对象
     */
    LogDemo selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 通过主键删除数据
     *
     * @param id 主键ID，自增
     * @return 数据库受影响行数
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /***
     * 插入实体对象
     * @param entity
     * @return 插入的条数
     */
    Integer insert(LogDemo entity);

    /**
    * 通过主键更新数据
    *
    * @param id 主键ID，自增
    * @return 数据库受影响行数
    */
    Integer updateByPrimaryKey(@Param("id") Integer id);
}
```
#### 输出Mapper(LogDemoMapper.xml) XML文件样例
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="temp.mapper.LogDemoMapper">
    <resultMap id="BaseResultMap"
               type="temp.entity.LogDemo">
            <id column="id" property="id"/>
            <result column="content" property="content"/>
            <result column="create_time" property="createTime"/>
            <result column="yn" property="yn"/>
    </resultMap>
    <!--所有查询列-->
    <sql id="QUERY_COLUMN_LIST">
        id,content,create_time,yn
    </sql>

    <!--根据主键获取实体对象-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap">
        SELECT
        <include refid="QUERY_COLUMN_LIST"/>
        FROM log_demo
        WHERE id = #{id} AND yn = 1
    </select>

    <!--通过主键删除数据-->
    <update id="deleteByPrimaryKey">
        UPDATE log_demo SET yn = -1 WHERE id = #{id} AND yn = 1
    </update>

    <!--插入实体对象-->
    <insert id="insert" parameterType="temp.entity.LogDemo" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO log_demo(id,content,create_time,yn)
        VALUES (#{id},#{content},#{createTime},#{yn})
    </insert>

    <!--通过主键更新数据-->
    <update id="updateByPrimaryKey">
        UPDATE log_demo
        <set>
            <if test="content != null and '' != content">content = #{content},</if>
            <if test="createTime != null and '' != createTime">createTime = #{create_time},</if>
            <if test="yn != null and '' != yn">yn = #{yn},</if>
        </set>
        WHERE id = #{id} AND yn = 1
    </update>

</mapper>
```


















