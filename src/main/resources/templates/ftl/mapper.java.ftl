package ${globalConfig.parentPacketName}.${globalConfig.mapperPacketName};

import ${globalConfig.parentPacketName}.${globalConfig.entityPacketName}.${tableInfo.entityName};
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${tableInfo.comment!} mapper层
 *
 * @Author: wei.wang7
 * @Date: 2020/7/26 17:09
 */
@Mapper
public interface ${tableInfo.mapperName} {
<#assign keyParam="" />
<#assign keyAnnotation="" />
<#list tableInfo.fields as field>
    <#if field.keyFlag>
        <#assign keyParam = keyParam + "@Param(\""+field.filedName+"\") "+ field.filedType.type +" "+  field.filedName +", "/>
        <#assign keyAnnotation = keyAnnotation + "* @param "+field.filedName+ " "+ field.comment+"\n"/>
    </#if>
</#list>
<#assign keyAnnotation = keyAnnotation?substring(0,keyAnnotation?length-1) />
<#assign keyParam = keyParam?substring(0,keyParam?length-2) />

    /**
     * 根据主键获取实体对象
     *
     ${keyAnnotation}
     * @return 获取的实体对象
     */
    ${tableInfo.entityName} selectByPrimaryKey(${keyParam});

    /**
     * 通过主键删除数据
     *
     ${keyAnnotation}
     * @return 数据库受影响行数
     */
    Integer deleteByPrimaryKey(${keyParam});

    /***
     * 插入实体对象
     * @param entity
     * @return 插入的条数
     */
    Integer insert(${tableInfo.entityName} entity);

    /**
    * 通过主键更新数据
    *
    ${keyAnnotation}
    * @return 数据库受影响行数
    */
    Integer updateByPrimaryKey(${keyParam});
}