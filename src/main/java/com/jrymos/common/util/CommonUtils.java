package com.jrymos.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/31 13:50
 * @Description:
 */
public class CommonUtils {
    public static boolean isEmpty(Object o) {
        return o == null ||
                ((o instanceof String) && ((String) o).isEmpty()) ||
                ((o instanceof Collection) && ((Collection) o).isEmpty()) ||
                ((o instanceof Map) && ((Map) o).isEmpty()) ||
                ((o instanceof Object[]) && ((Object[]) o).length == 0);
    }

    public static <T> T any(T[] ts, T defaultValue) {
        if (ts == null || ts.length == 0) {
            return defaultValue;
        }
        return ts[0];
    }

    public static <E> E lastElement(List<E> eList) {
        if (eList == null || eList.isEmpty()) {
            return null;
        }
        return eList.get(eList.size() - 1);
    }

    public static <E> E lastElement(E[] es) {
        if (es == null || es.length == 0) {
            return null;
        }
        return es[es.length - 1];
    }

    public static <T> T[] combine(IntFunction<T[]> generator, T[] ts1, T... ts2) {
        T[] result = generator.apply(ts1.length + ts2.length);
        System.arraycopy(ts1, 0, result, 0, ts1.length);
        System.arraycopy(ts2, 0, result, ts1.length, ts2.length);
        return result;
    }

    public static Object[] combine(Object... objects) {
        LinkedList<Object> objectLinkedList = new LinkedList<>();
        for (Object o : objects) {
            if (o instanceof Object[]) {
                objectLinkedList.addAll(Arrays.asList((Object[]) o));
            } else if (o instanceof Collection) {
                objectLinkedList.addAll((Collection) o);
            } else {
                objectLinkedList.add(o);
            }
        }
        return objectLinkedList.toArray();
    }

    public static boolean isArray(Object array) {
        return array instanceof Object[] ||
                array instanceof boolean[] ||
                array instanceof byte[] ||
                array instanceof char[] ||
                array instanceof double[] ||
                array instanceof float[] ||
                array instanceof int[] ||
                array instanceof long[] ||
                array instanceof short[];
    }

    public static String toString(Object array) {
        return array == null ? null
                : array instanceof Object[] ? Arrays.toString((Object[]) array)
                : array instanceof boolean[] ? Arrays.toString((boolean[]) array)
                : array instanceof byte[] ? Arrays.toString((byte[]) array)
                : array instanceof char[] ? Arrays.toString((char[]) array)
                : array instanceof double[] ? Arrays.toString((double[]) array)
                : array instanceof float[] ? Arrays.toString((float[]) array)
                : array instanceof int[] ? Arrays.toString((int[]) array)
                : array instanceof long[] ? Arrays.toString((long[]) array)
                : array instanceof short[] ? Arrays.toString((short[]) array)
                : array.toString();
    }
}
