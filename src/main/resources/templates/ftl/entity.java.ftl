package ${globalConfig.parentPacketName}.${globalConfig.entityPacketName};

import java.io.Serializable;
<#list importPackages as pkg>
import ${pkg};
</#list>

/**
 * ${tableInfo.comment!}
 *
 * @Author ${globalConfig.author!}
 * @Date ${.now}
 */
<#if globalConfig.entityLombokModel>
@Data
</#if>
public class ${tableInfo.entityName} implements Serializable{

    private static final long serialVersionUID=1L;

<#--静态字段常量-->
<#if globalConfig.entityColumnConstant>
    /**
     * 数据库静态常量字段列
     */
    <#list tableInfo.fields as field>
    public static final String FIELD_${field.name?upper_case} = "${field.filedName}";
    </#list>
</#if>

<#--字段-->
<#list tableInfo.fields as field>
    /**
     * ${field.comment!}
     */
    private ${field.filedType.type} ${field.filedName};
</#list>

<#--get,set方法-->
<#if !globalConfig.entityLombokModel>
    <#list tableInfo.fields as field>
        <#if field.filedType.type == "boolean">
             <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.filedType.type} ${getprefix}${field.capitalName}() {
        return ${field.filedName};
    }
    public void set${field.capitalName}(${field.filedType.type} ${field.filedName}){
        this.${field.filedName} = ${field.filedName};
    }
    </#list>
</#if>
}