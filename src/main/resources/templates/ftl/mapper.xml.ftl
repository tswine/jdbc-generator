<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${globalConfig.parentPacketName}.${globalConfig.mapperPacketName}.${tableInfo.mapperName}">
    <#assign keyParam=""/>
    <resultMap id="BaseResultMap"
               type="${globalConfig.parentPacketName}.${globalConfig.entityPacketName}.${tableInfo.entityName}">
        <#list tableInfo.fields as field>
            <#if field.keyFlag>
                <#assign columnFlag="id"/>
                <#assign keyParam = keyParam + field.name +" = " + "#\{"+field.filedName+"}"+" AND "/>
            <#else>
                <#assign columnFlag="result"/>
            </#if>
            <${columnFlag} column="${field.name}" property="${field.filedName}"/>
        </#list>
    </resultMap>
    <#assign keyParam = keyParam?substring(0,keyParam?length-5)/>
    <#assign allColumns="" />
    <#assign allColumnParams="" />
    <#list tableInfo.fields as field>
        <#assign allColumns = allColumns+field.name +","/>
        <#assign allColumnParams = allColumnParams+ "#\{"+field.filedName+"}" +","/>
    </#list>
    <#assign allColumns= allColumns?substring(0,allColumns?length-1)/>
    <#assign allColumnParams=allColumnParams?substring(0,allColumnParams?length-1) />
    <#--是否存在逻辑删除-->
    <#if tableInfo.logicDelete>
        <#assign logicNotDeleteWhere = "AND " + tableInfo.fieldLogicDelete.name + " = " +  tableInfo.fieldLogicDelete.notDeleteValue />
        <#assign logicDeleteWhere = "AND " + tableInfo.fieldLogicDelete.name + " = " +  tableInfo.fieldLogicDelete.deleteValue />
    </#if>
    <#--是否存在主键自增-->
    <#if tableInfo.keyAutoIncrement>
        <#assign keyAutoIncrementSql= "useGeneratedKeys=\"true\" keyProperty=\""+tableInfo.primaryFields[0].name+"\""/>
    </#if>
    <!--所有查询列-->
    <sql id="QUERY_COLUMN_LIST">
        ${allColumns}
    </sql>

    <!--根据主键获取实体对象-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap">
        SELECT
        <include refid="QUERY_COLUMN_LIST"/>
        FROM ${tableInfo.name}
        WHERE ${keyParam} ${logicNotDeleteWhere!}
    </select>

    <!--通过主键删除数据-->
    <#if tableInfo.logicDelete>
    <update id="deleteByPrimaryKey">
        UPDATE ${tableInfo.name} SET ${tableInfo.fieldLogicDelete.name} = ${tableInfo.fieldLogicDelete.deleteValue} WHERE ${keyParam} ${logicNotDeleteWhere!}
    </update>
    <#else>
    <delete id="deleteByPrimaryKey">
        DELETE FROM ${tableInfo.name} WHERE ${keyParam}
    </delete>
    </#if>

    <!--插入实体对象-->
    <insert id="insert" parameterType="${globalConfig.parentPacketName}.${globalConfig.entityPacketName}.${tableInfo.entityName}" ${keyAutoIncrementSql!}>
        INSERT INTO ${tableInfo.name}(${allColumns})
        VALUES (${allColumnParams})
    </insert>

    <!--通过主键更新数据-->
    <update id="updateByPrimaryKey">
        UPDATE ${tableInfo.name}
        <set>
            <#list tableInfo.generalFields as field>
            <#assign conditon= "#\{"+field.name+"},"/>
            <if test="${field.filedName} != null and '' != ${field.filedName}">${field.filedName} = ${conditon}</if>
            </#list>
        </set>
        WHERE ${keyParam} ${logicNotDeleteWhere!}
    </update>

</mapper>