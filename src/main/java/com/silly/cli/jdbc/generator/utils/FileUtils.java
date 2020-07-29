package com.silly.cli.jdbc.generator.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 文件工具类
 *
 * @Author: wei.wang7
 * @Date: 2020/7/26 14:01
 */
@Slf4j
public class FileUtils {


    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static boolean mkdirs(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            log.info("创建文件目录:{},创建结果:{}", path, mkdirs);
            return mkdirs;
        }
        log.info("文件目录已经存在:{}", path);
        return true;
    }
}
