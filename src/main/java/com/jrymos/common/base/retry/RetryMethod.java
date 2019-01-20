package com.jrymos.common.base.retry;

/**
 * Created with IntelliJ IDEA
 * Description: 重试方法
 * User: weijun.zou
 * Date: 2019-01-18
 * Time: 16:51
 */
public interface RetryMethod {
    void run(Object... args);
}
