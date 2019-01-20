package com.jrymos.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/11/4 18:53
 * @Description: 反射工具类
 */
public class ClassUtils {

    @SuppressWarnings("unchecked")
    public static <T> T get(Object bean, String fieldName, T defaultValue) {
        return get(bean, fieldName, () -> defaultValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Object bean, String fieldName, Supplier<T> defaultValue) {
        if (Objects.isNull(bean) || StringUtils.isEmpty(fieldName)) {
            return defaultValue.get();
        }
        try {
            Field field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object o = field.get(bean);

            return (T) o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return defaultValue == null ? null : defaultValue.get();
        }
    }

    public static Class getFirstGeneric(Class baseClass) {
        Type type = baseClass.getGenericInterfaces()[0];
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type genericClass = pType.getActualTypeArguments()[0];
            if (genericClass instanceof Class) {
                return (Class) genericClass;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <RESULT> RESULT invoke(Object bean, String methodName, Class[] parameterTypes, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (RESULT) bean.getClass().getMethod(methodName, parameterTypes).invoke(bean, parameters);
    }

    @SuppressWarnings("unchecked")
    public static <RESULT> RESULT invokeStatic(Class beanClass, String methodName, Class[] parameterTypes, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (RESULT) beanClass.getMethod(methodName, parameterTypes).invoke(null, parameters);
    }

}
