package com.jrymos.common.exception;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/13 11:00
 * @Description:
 */
public interface TryCatch<T> {
    T doSomeThing() throws Throwable;
}
