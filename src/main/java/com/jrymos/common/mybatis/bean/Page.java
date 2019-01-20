package com.jrymos.common.mybatis.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.jrymos.common.util.CommonUtils.lastElement;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/30 19:48
 * @Description: 分页查询
 */
@Getter
@Setter
@ToString
public class Page<ENTITY> {
    // 过滤条件
    private ENTITY entity;

    // 查询位置和数量控制
    private long idOffset;
    private int pageSize;
    private int pageIndex;

    private boolean lastPage = false;

    //=============时间条件过滤=================
    private Date startDate;
    private Date endDate;
    // true使用updateTime，false使用createTime
    private boolean useUpdateTime = false;

    /**
     * 更新分页信息
     */
    public void updatePage(List<ENTITY> entityList, Function<ENTITY, Long> idProperty) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            idOffset = idProperty.apply(lastElement(entityList));
            pageIndex++;
            if (entityList.size() < pageSize) {
                lastPage = true;
            }
        } else {
            lastPage = true;
        }
    }
}
