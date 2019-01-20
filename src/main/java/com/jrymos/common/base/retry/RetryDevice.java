package com.jrymos.common.base.retry;

import com.alibaba.fastjson.JSON;
import com.jrymos.common.annotation.Retry;
import com.jrymos.common.exception.CommonException;
import com.jrymos.common.storage.BaseStorage;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User: weijun.zou
 * Date: 2019-01-18
 * Time: 16:41
 */
@Component
public class RetryDevice implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryDevice.class);

    private static final String RETRY_DEVICE_QUEUE_KEY = "RETRY_DEVICE_QUEUE_KEY";

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BaseStorage baseStorage;

    private Map<String, RetryMethod> retryMethodMap;

    private Map<String, Retry> retryMap;

    @Pointcut(
            value = "@within(retry) || @annotation(retry)",
            argNames = "retry"
    )
    public void retryCallPointcut(Retry retry) {
    }

    @Around("retryCallPointcut(retry)")
    @SuppressWarnings("unchecked")
    public Object aspectRetryCall(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            return handThrowable(e, retry, newRetryBean(joinPoint, retry));
        }
    }

    public void consumeRetryInfo() {
        RetryInfo retryInfo = JSON.parseObject(baseStorage.pop(RETRY_DEVICE_QUEUE_KEY), RetryInfo.class);
        RetryMethod retryMethod = retryMethodMap.get(retryInfo.getKey());
        try {
            retryMethod.run(retryInfo.getArgs());
        } catch (Throwable e) {
            CommonException.tryCatch(() -> {
                handThrowable(e, retryMap.get(retryInfo.getKey()), retryInfo);
                LOGGER.error("consumeRetryBean#retryInfo:{}", retryInfo, e);
                return null;
            });
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String beans[] = applicationContext.getBeanDefinitionNames();
        for (String beanString : beans) {
            Object bean = applicationContext.getBean(beanString);
            Method[] methods = bean.getClass().getMethods();
            if (ArrayUtils.isEmpty(methods)) {
                continue;
            }
            for (Method method : methods) {
                Retry retry = method.getAnnotation(Retry.class);
                if (retry == null) {
                    continue;
                }
                addRetryMethod(bean, method, retry);
            }
        }
    }

    private void addRetryMethod(Object bean, Method method, Retry retry) {
        // 生成key
        String key = newKey(bean.getClass().getName(), method.getName(), retry.id());
        // 生成RetryMethod 生成动态代理类 TODO
        RetryMethod retryMethod = null;
        retryMethodMap.put(key, retryMethod);
        retryMap.put(key, retry);
    }

    private RetryInfo newRetryBean(ProceedingJoinPoint joinPoint, Retry retry) {
        String key = newKey(joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), retry.id());
        RetryInfo retryInfo = new RetryInfo();
        retryInfo.setKey(key);
        retryInfo.setArgs(joinPoint.getArgs());
        retryInfo.setTimes(retry.times());
        return retryInfo;
    }

    private static String newKey(String className, String methodName, String id) {
        return className + "_" + methodName + "_" + id;
    }


    private Object handThrowable(Throwable e, Retry retry, RetryInfo retryInfo) throws Throwable {
        for (Class tClass : retry.retryFor()) {
            if (tClass.isInstance(e)) {
                retryInfo.setTimes(retryInfo.getTimes() - 1);
                baseStorage.push(RETRY_DEVICE_QUEUE_KEY, JSON.toJSONString(retryInfo));
                throw e;
            }
        }
        for (Class tClass : retry.notRetryFor()) {
            if (tClass.isInstance(e)) {
                throw e;
            }
        }
        baseStorage.push(RETRY_DEVICE_QUEUE_KEY, JSON.toJSONString(retryInfo));
        throw e;
    }

}
