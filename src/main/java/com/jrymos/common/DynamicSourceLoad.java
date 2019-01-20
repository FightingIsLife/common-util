package com.jrymos.common;

import com.google.common.io.Files;
import com.jrymos.common.exception.CommonException;
import com.jrymos.common.mybatis.SqlBuilderFactory;
import com.jrymos.common.util.JavaSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2019/1/20 18:22
 * @Description: 动态类资源加载
 */
public class DynamicSourceLoad {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSourceLoad.class);

    public static String baseSqlBuilderTemplate;
    public static String entityPageSqlBuilderTemplate;
    public static String retryMethodTemplate;

    /*
     * 读取源码模板
     */
    static {
        initTemplate();
    }

    public static void initTemplate(){
        try {
            baseSqlBuilderTemplate = Files.toString(new File(SqlBuilderFactory.class.getResource("/javaSource/BaseSqlBuilderTemplate.java").getFile())
                    , Charset.forName("UTF-8"));
            entityPageSqlBuilderTemplate = Files.toString(new File(SqlBuilderFactory.class.getResource("/javaSource/EntityPageSqlBuilderTemplate.java").getFile())
                    , Charset.forName("UTF-8"));
            retryMethodTemplate = Files.toString(new File(SqlBuilderFactory.class.getResource("/javaSource/RetryMethodTemplate.java").getFile())
                    , Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态加载类、创建类
     * @return 返回该类的一个实例
     */
    public static <T> T newInstance(String javaSource) {
        return newInstance(javaSource, null);
    }

    /**
     * 动态加载类、创建类
     * @return 返回该类的一个实例
     */
    public static <T> T newInstance(String javaSource, Class[] argsClass, Object... args) {
        String className = javaSource.substring(javaSource.indexOf('\\') + 2, javaSource.indexOf('\n')).trim();
        int index = javaSource.indexOf("package") + "package".length();
        String packageName = javaSource.substring(index, javaSource.indexOf(';', index));
        LOGGER.info("newInstance#package:{},className:{}", packageName, className);
        return CommonException.tryCatch(() -> JavaSourceUtils.newInstance(packageName, className, javaSource, argsClass, args));
    }
}
