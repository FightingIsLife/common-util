/*
 * Copyright (C) 2014 Qunar All rights reserved.
 *
 */
package com.jrymos.common.base;

import com.jrymos.common.exception.CommonException;
import com.jrymos.common.exception.CommonExceptionEnum;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author fly
 */
public class Assert {
    /**
     * Assert that an object is not {@code null} .
     * <pre class="source">Assert.notNull(clazz);</pre>
     *
     * @param object the object to check
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that an object is not {@code null} .
     * <pre class="source">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }

    /**
     * <p>Assert that an object is not {@code null} or not empty</p>
     *
     * @param str
     * @return void
     */
    public static void notEmpty(String str) {
        notEmpty(str, "[Assertion failed] - this argument is required; it must not be null or empty");
    }

    public static void notEmpty(Object object, String message) {
        notNull(object, message);
        if (object instanceof String) {
            Assert.notEmpty((String) object, message);
        } else if (object instanceof Collection) {
            Assert.notEmpty((Collection) object, message);
        } else if (object instanceof Map) {
            Assert.notEmpty((Map) object, message);
        } else if (object instanceof Object[]) {
            Assert.notEmpty((Object[]) object, message);
        }
    }

    /**
     * <p>Assert that an object is not {@code null} or not empty</p>
     *
     * @param str
     * @param message
     * @return void
     */
    public static void notEmpty(String str, String message) {
        if (str == null || str.trim().length() == 0) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <source>null</source> and must have at least one element.
     * <pre class="source">Assert.notEmpty(collection, "Collection must have elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is <source>null</source> or has no elements
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="source">Assert.notEmpty(map);</pre>
     *
     * @param map the map to check
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="source">Assert.notEmpty(map, "Map must have entries");</pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="source">Assert.notEmpty(array);</pre>
     *
     * @param array the array to check
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="source">Assert.notEmpty(array, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (isEmpty(array)) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }

    /**
     * Determine whether the given array is empty:
     * i.e. {@code null} or of zero length.
     *
     * @param array the array to check
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 是否相等
     *
     * @param message
     * @param condition
     */
    static public void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new CommonException(CommonExceptionEnum.PARAM_ERROR, message);
        }
    }
}
