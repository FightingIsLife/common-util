package com.jrymos.common.mybatis.mapper;

import com.jrymos.common.mybatis.SqlProviderContainer;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/30 19:44
 * @Description:
 */
public interface CommonMapper<ENTITY> {

    @UpdateProvider(type = SqlProviderContainer.class, method = "update")
    int update(ENTITY entity);

    default ENTITY selectById(int id) {
        return selectById(this, id);
    }

    @SelectProvider(type = SqlProviderContainer.class, method = "selectById")
    ENTITY selectById(CommonMapper commonMapper, @Param("id") int id);

    @InsertProvider(type = SqlProviderContainer.class, method = "insert")
    @SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    void insert(ENTITY entity);
}
