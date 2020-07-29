package com.silly.cli.jdbc.generator.config.rules;

import com.silly.cli.jdbc.generator.utils.StringPool;
import com.silly.cli.jdbc.generator.utils.StringUtils;

import java.util.Arrays;

/**
 * 命名策略
 *
 * @Author: wei.wang7
 * @Date: 2020/7/12 18:42
 */
public enum NameStrategy {

    /**
     * 原样输出
     */
    NO_CHANGE,
    /**
     * 下划线驼峰命名
     */
    UNDERLINE_TO_CAMEL;


    /**
     * 转换名称
     *
     * @param name         需要转换的字段
     * @param nameStrategy 命名策略
     * @return
     */
    public static String convertName(String name, NameStrategy nameStrategy, Integer type) {
        if (nameStrategy == null) {
            return name;
        }
        switch (nameStrategy) {
            case UNDERLINE_TO_CAMEL:
                name = underlineToCamel(name);
                if (type == 2) {
                    name = StringUtils.capitalFirst(name);
                }
                return name;
            default:
                return name;
        }
    }


    /**
     * 驼峰命名转换
     *
     * @param name
     * @return
     */
    private static String underlineToCamel(String name) {
        if (StringUtils.isBlank(name)) {
            return StringPool.EMPTY;
        }
        //将所有的字符都转换为小写
        String tmpName = name.toLowerCase();
        StringBuilder sb = new StringBuilder();
        String[] camels = tmpName.split(StringPool.UNDERLINE);
        Arrays.stream(camels).filter(camel -> StringUtils.isNotBlank(camel)).forEach(camel -> {
            if (sb.length() == 0) {
                sb.append(camel);
            } else {
                //首字母大写
                sb.append(StringUtils.capitalFirst(camel));
            }
        });
        return sb.toString();
    }


}
