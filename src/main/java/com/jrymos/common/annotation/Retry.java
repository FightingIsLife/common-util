package com.jrymos.common.annotation;

import com.jrymos.common.base.retry.RetryInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2019/1/20 17:22
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    /**
     * 重试次数
     */
    int times() default 3;

    /**
     * 捕捉的重试的异常,默认捕捉所有异常
     *
     * @see com.jrymos.common.base.retry.RetryDevice#handThrowable(Throwable, Retry, RetryInfo)
     */
    Class<? extends Throwable>[] retryFor() default {};

    /**
     * 不捕捉重试的异常
     */
    Class<? extends Throwable>[] notRetryFor() default {};

    /**
     * 唯一标识(区分同一个类的相同方法名)， 类名+"_"+方法名+"_"+标识
     *
     */
    String id() default "";
}
