package com.jrymos.common.mybatis.mapper;

import com.jrymos.common.mybatis.SqlProviderContainer;
import com.jrymos.common.mybatis.bean.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/31 16:59
 * @Description:
 */
public interface PageMapper<ENTITY> {

    /**
     * 分页查询
     */
    @SelectProvider(type = SqlProviderContainer.class, method = "selectByEntityPage")
    List<ENTITY> selectByEntityPage(@Param("mybatis") PageMapper pageMapper, @Param("data") Page<ENTITY> page);

    default List<ENTITY> selectByEntityPage(Page<ENTITY> page) {
        return selectByEntityPage(this, page);
    }
}
