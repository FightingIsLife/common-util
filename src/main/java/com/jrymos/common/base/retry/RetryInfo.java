package com.jrymos.common.base.retry;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User: weijun.zou
 * Date: 2019-01-18
 * Time: 18:21
 */
public class RetryInfo {
    private int times;
    private String key;
    private Object[] args;

    @Override
    public String toString() {
        return "RetryInfo{" +
                "times=" + times +
                ", key='" + key + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
