package com.jrymos.common.mybatis;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/31 12:17
 * @Description: 动态生成sql
 */
public interface SqlBuilder {
    /**
     * 根据参数动态生成sql
     * @return 一条sql
     */
    String build(Object o);
}
