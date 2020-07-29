package com.silly.cli.jdbc.generator.engine;

import com.silly.cli.jdbc.generator.config.GlobalConfig;
import com.silly.cli.jdbc.generator.config.po.TableInfo;

import java.util.List;

/**
 * 模板引擎
 *
 * @Author: wei.wang7
 * @Date: 2020/7/19 12:18
 */
public interface ITemplateEngine {


    /**
     * 初始化
     *
     * @param tableInfos   表信息
     * @param globalConfig 全局配置
     */
    ITemplateEngine init(List<TableInfo> tableInfos, GlobalConfig globalConfig);


    /**
     * 生成
     */
    void generate();

}
