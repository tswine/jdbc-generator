package com.silly.cli.jdbc.generator.engine;

import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.po.TableInfo;
import com.silly.cli.jdbc.generator.config.rules.FileType;
import com.silly.cli.jdbc.generator.utils.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * Freemarker 模板引擎
 *
 * @Author: wei.wang7
 * @Date: 2020/7/19 12:48
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    @Override
    public ITemplateEngine init(List<TableInfo> tableInfos, GlobalConfig globalConfig) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(Constants.UTF8);
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, Constants.SLASH);
        this.tableInfos = tableInfos;
        this.globalConfig = globalConfig;
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, Constants.UTF8));
        }
    }

    @Override
    public String templateFilePath(FileType fileType) {
        switch (fileType) {
            case ENTITY:
                return "/templates/ftl/entity.java.ftl";
            case MAPPER:
                return "/templates/ftl/mapper.java.ftl";
            case XML:
                return "/templates/ftl/mapper.xml.ftl";
        }
        return null;

    }

}
