package com.silly.cli.jdbc.generator.engine;

import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.po.TableInfo;
import com.silly.cli.jdbc.generator.config.rules.FileType;
import com.silly.cli.jdbc.generator.utils.Constants;
import com.silly.cli.jdbc.generator.utils.FileUtils;
import com.silly.cli.jdbc.generator.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象模板引擎
 *
 * @Author: wei.wang7
 * @Date: 2020/7/19 12:18
 */
public abstract class AbstractTemplateEngine implements ITemplateEngine {

    /**
     * 表信息
     */
    protected List<TableInfo> tableInfos;
    /**
     * 全局配置
     */
    protected GlobalConfig globalConfig;

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 模板真实文件路径
     *
     * @param fileType
     * @return
     */
    public abstract String templateFilePath(FileType fileType);


    @Override
    public void generate() {
        try {
            //实体文件目录:数据目录+父包名+实体包名
            String baseFilePath = globalConfig.getOutputDir() + File.separator
                    + StringUtils.pointToSeparator(globalConfig.getParentPacketName());
            String entityFilePath = baseFilePath + File.separator + globalConfig.getEntityPacketName();
            FileUtils.mkdirs(entityFilePath);
            String mapperFilePath = baseFilePath + File.separator + globalConfig.getMapperPacketName();
            FileUtils.mkdirs(mapperFilePath);
            String xmlFilePath = baseFilePath + File.separator + globalConfig.getXmlPacketName();
            FileUtils.mkdirs(xmlFilePath);
            for (TableInfo tableInfo : tableInfos) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                //entity.java
                String entityFile = entityFilePath + File.separator + tableInfo.getEntityName() + Constants.SUFFIX_JAVA;
                output(objectMap, FileType.ENTITY, entityFile);
                //mapper.java
                String mapperFile = mapperFilePath + File.separator + tableInfo.getMapperName() + Constants.SUFFIX_JAVA;
                output(objectMap, FileType.MAPPER, mapperFile);
                //xml
                String xmlFile = xmlFilePath + File.separator + tableInfo.getXmlName() + Constants.SUFFIX_XML;
                output(objectMap, FileType.XML, xmlFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 输出文件
     *
     * @param objectMap 模板参数
     * @param fileType  文件类型
     * @param filePath  文件目录
     */
    private void output(Map<String, Object> objectMap, FileType fileType, String filePath) throws Exception {
        //不允许文件覆盖
        if (!globalConfig.isOverride()) {
            File file = new File(filePath);
            if (file.exists()) {
                throw new RuntimeException("文件已经存在，不允许覆盖,文件路径:" + filePath);
            }
        }
        writer(objectMap, templateFilePath(fileType), filePath);
    }


    /**
     * 获取渲染到模板的参数信息
     *
     * @param tableInfo
     * @return
     */
    private Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>();
        //表信息
        objectMap.put("tableInfo", tableInfo);
        objectMap.put("importPackages", tableInfo.getImportPackages());
        //全局配置
        objectMap.put("globalConfig", globalConfig);
        return objectMap;
    }

}
