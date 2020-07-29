package com.silly.cli.jdbc.generator.config.po;

import com.silly.cli.jdbc.generator.utils.CollectionUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表信息
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 20:45
 */
@Data
public class TableInfo {
    /**
     * 需要导入的包
     */
    private final Set<String> importPackages = new HashSet<>();

    /**
     * 表名
     */
    private String name;
    /**
     * 表注解
     */
    private String comment;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * mapper名称
     */
    private String mapperName;
    /**
     * xml名称
     */
    private String xmlName;

    /**
     * 是否逻辑删除
     */
    private boolean logicDelete;

    /**
     * 逻辑删除字段信息
     */
    private FieldLogicDelete fieldLogicDelete;

    /**
     * 所有字段信息
     */
    private List<TableField> fields = new ArrayList<>();


    /**
     * 获取主键列字段
     *
     * @return
     */
    public List<TableField> getPrimaryFields() {
        List<TableField> primaryFields = new ArrayList<>();
        fields.forEach(k -> {
            if (k.isKeyFlag()) {
                primaryFields.add(k);
            }
        });
        return primaryFields;
    }

    /**
     * 获取普通列字段
     *
     * @return
     */
    public List<TableField> getGeneralFields() {
        List<TableField> generalFields = new ArrayList<>();
        fields.forEach(k -> {
            if (!k.isKeyFlag()) {
                generalFields.add(k);
            }
        });
        return generalFields;
    }

    /**
     * 主键是否自增
     *
     * @return
     */
    public boolean isKeyAutoIncrement() {
        List<TableField> primaryFields = getPrimaryFields();
        if (CollectionUtils.isEmpty(primaryFields) && primaryFields.size() != 1) {
            return false;
        }
        TableField tableField = primaryFields.get(0);
        return tableField.isKeyAutoIncrement();

    }


}
