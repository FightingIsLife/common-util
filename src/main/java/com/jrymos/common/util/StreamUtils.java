package com.jrymos.common.util;


import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA
 * Description: java stream 工具类
 * User: weijun.zou
 * Date: 2018-11-20
 * Time: 12:11
 */
public class StreamUtils {

    /**
     * reduce(last()) 取最后一个元素
     */
    public static <T> BinaryOperator<T> last() {
        return (element, nextElement) -> nextElement;
    }

    /**
     * reduce(max()) 取最大一个元素,如果两个元素相等,取第一个
     *
     * @see BinaryOperator#maxBy(Comparator) 另一种用法
     */
    public static <T, U extends Comparable<? super U>> BinaryOperator<T> max(Function<? super T, ? extends U> keyExtractor) {
        return (element, nextElement) -> keyExtractor.apply(element).compareTo(keyExtractor.apply(nextElement)) >= 0 ? element : nextElement;
    }

    /**
     * reduce(min()) 取最小一个元素,如果两个元素相等,取第一个
     *
     * @see BinaryOperator#minBy(Comparator) 另一种用法
     */
    public static <T, U extends Comparable<? super U>> BinaryOperator<T> min(Function<? super T, ? extends U> keyExtractor) {
        return (element, nextElement) -> keyExtractor.apply(element).compareTo(keyExtractor.apply(nextElement)) <= 0 ? element : nextElement;
    }

    /**
     * filter(equals()) 保留某个属性和和compare相等的元素
     * 等价于 <=> filter(o-> Objects.equals(o.getXXX(),compare))
     */
    public static <T, U> Predicate<? super T> equals(Function<? super T, ? extends U> keyExtractor, U compare) {
        return element -> Objects.equals(keyExtractor.apply(element), compare);
    }

    /**
     * filter(contains()) 保留某个属性在集合中的元素
     * 等价于 <=> filter(o-> collections.contains(o.getXXX()))
     */
    public static <T, U> Predicate<? super T> contains(Collection<U> collection, Function<? super T, U> keyExtractor) {
        return element -> collection.contains(keyExtractor.apply(element));
    }

    /**
     * filter(not()) 取反过滤
     * 等价于 <=> filter(t->!predicate.test(t))
     */
    public static <T> Predicate<? super T> not(Predicate<? super T> predicate) {
        return (Predicate<T>) t -> !predicate.test(t);
    }

    /**
     * 两次转换 L -> M -> R
     */
    public static <L, M, R> Function<L, R> trans(Function<L, M> firstTrans, Function<M, R> secondTrans) {
        return object -> secondTrans.apply(firstTrans.apply(object));
    }

    /**
     * 安全的生成stream，避免空指针
     */
    public static <T> Stream<T> toStream(Collection<T> collection) {
        if (collection == null) {
            return Stream.empty();
        }
        return collection.stream();
    }
}
