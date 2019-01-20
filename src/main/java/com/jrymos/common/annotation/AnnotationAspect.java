package com.jrymos.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2019/1/20 17:10
 * @Description:
 */
@Component
public class AnnotationAspect {

    private final static Map<Class, Logger> LOGGER_MAP = new ConcurrentHashMap<>(200);

    private static Logger getLogger(ProceedingJoinPoint joinPoint) {
        return LOGGER_MAP.compute(joinPoint.getTarget().getClass(),
                (targetClass, logger) -> logger == null ? LoggerFactory.getLogger(targetClass) : logger);
    }
    //=======================================================================================
}
