package com.jrymos.common.storage;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2019/1/20 14:04
 * @Description: 存储，所有方法的所有参数不能空
 */
public interface BaseStorage {

    /**
     * @param key   设置key
     * @param value 元素不能为空
     */
    default void set(String key, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 类似redis中的setnx
     *
     * @return true 设置成功，false设置失败
     */
    default boolean setnx(String key, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 如果不存在，返回null
     */
    default String get(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 往队列中添加元素，队列不存在则先创建队列，再添加元素，元素不能为空
     */
    default void push(String queueName, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return 队列为空或者队列不存在，返回null
     */
    default String pop(String queueName) {
        throw new UnsupportedOperationException();
    }
}
