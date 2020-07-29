package com.silly.cli.jdbc.generator.utils;

import java.nio.charset.StandardCharsets;

/**
 * 常量
 *
 * @Author: wei.wang7
 * @Date: 2020/7/19 11:36
 */
public interface Constants {

    /**
     * 默认父包名
     */
    String DEFAULT_PARENT_PACKAGE = "com.xxx";

    /**
     * mapper包名
     */
    String ENTITY_PACKAGE_NAME = "entity";

    /**
     * mapper类名格式
     */
    String MAPPER_CLASS_NAME_FORMAT = "%sMapper";

    /**
     * mapper包名
     */
    String MAPPER_PACKAGE_NAME = "mapper";

    /**
     * xml 文件格式
     */
    String XML_CLASS_NAME_FORMAT = "%sMapper";

    /**
     * xml包名
     */
    String XML_PACKAGE_NAME = "xml";

    /**
     * utf-8编码
     */
    String UTF8 = StandardCharsets.UTF_8.name();

    /**
     * java文件后缀
     */
    String SUFFIX_JAVA = ".java";

    /**
     * xml文件后缀
     */
    String SUFFIX_XML = ".xml";

    /**
     *
     */
    String SLASH = "/";

}
