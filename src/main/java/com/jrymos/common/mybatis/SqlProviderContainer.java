package com.jrymos.common.mybatis;

import com.jrymos.common.mybatis.bean.Page;
import com.jrymos.common.mybatis.mapper.CommonMapper;
import com.jrymos.common.mybatis.mapper.PageMapper;
import com.jrymos.common.util.StringTransUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jrymos.common.util.ClassUtils.get;
import static com.jrymos.common.util.ClassUtils.getFirstGeneric;
import static com.jrymos.common.util.StreamUtils.trans;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/30 21:28
 * @Description:
 */
@Component
public class SqlProviderContainer implements InitializingBean {

    // key: entity class
    private static Map<Class, SqlBuilder> updateSqlMap = new HashMap<>();
    // key: CommonMapper class
    private static Map<Class, SqlBuilder> selectByIdSqlMap = new HashMap<>();
    // key: entity class
    private static Map<Class, SqlBuilder> insertSqlMap = new HashMap<>();
    // key: PageMapper class
    private static Map<Class, SqlBuilder> entityPageSqlMap = new HashMap<>();


    //====================================获取sql=================================================
    public static <ENTITY> String update(ENTITY entity) {
        return updateSqlMap.get(entity.getClass()).build(entity);
    }

    public static <ENTITY> String selectById(CommonMapper mapper) {
        return selectByIdSqlMap.get(mapper.getClass()).build(null);
    }

    public static <ENTITY> String insert(ENTITY entity) {
        return insertSqlMap.get(entity.getClass()).build(entity);
    }

    public static <ENTITY> String selectByEntityPage(@Param("mybatis") PageMapper<ENTITY> pageMapper,
                                                                    @Param("data") Page<ENTITY> page) {
        return entityPageSqlMap.get(pageMapper.getClass()).build(page);
    }

    //===========================init sqlMap after spring injection===============================

    @Resource
    private List<CommonMapper> baseMapperList;

    @Resource
    private List<PageMapper> pageMapperList;

    @Override
    public void afterPropertiesSet() throws Exception {
        toMapperEntityClassMap(baseMapperList)
                .forEach((proxyMapper, entityClass) -> {
                    String tableName = StringTransUtils.humpToUnderline(entityClass.getSimpleName());
                    Map<String, Field> columnFieldMap = Arrays.stream(entityClass.getDeclaredFields())
                            .collect(toMap(trans(Field::getName, StringTransUtils::humpToUnderline), identity()));
                    updateSqlMap.put(entityClass, SqlBuilderFactory.updateSqlBuilder(entityClass, tableName, columnFieldMap));
                    selectByIdSqlMap.put(proxyMapper.getClass(), SqlBuilderFactory.selectByIdSqlBuilder(entityClass, tableName, columnFieldMap));
                    insertSqlMap.put(entityClass, SqlBuilderFactory.insertSqlBuilder(entityClass, tableName, columnFieldMap));
                });
        toMapperEntityClassMap(pageMapperList)
                .forEach((proxyMapper, entityClass) -> {
                    String tableName = StringTransUtils.humpToUnderline(entityClass.getSimpleName());
                    Map<String, Field> columnFieldMap = Arrays.stream(entityClass.getDeclaredFields())
                            .collect(toMap(trans(Field::getName, StringTransUtils::humpToUnderline), identity()));
                    entityPageSqlMap.put(proxyMapper.getClass(), SqlBuilderFactory.selectByEntityPage(entityClass, tableName, columnFieldMap));
                });
        SqlBuilderFactory.destroy();
    }

    private static <MAPPER> Map<MAPPER, Class> toMapperEntityClassMap(List<MAPPER> mapperList) {
        if (CollectionUtils.isEmpty(mapperList)) {
            return Collections.emptyMap();
        }
        Map<MAPPER, Class> mapperClassMap = new HashMap<>(mapperList.size());
        for (MAPPER mapper : mapperList) {
            mapperClassMap.put(mapper, getFirstGeneric(trans2RealClass(mapper)));
        }
        return mapperClassMap;
    }

    private static <MAPPER> Class trans2RealClass(MAPPER proxy) {
        return get(Proxy.getInvocationHandler(proxy), "mapperInterface", null);
    }
}
