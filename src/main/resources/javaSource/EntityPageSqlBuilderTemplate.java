package com.jsrmos.sasuke.dynamic.mapper;

import com.jsrmos.sasuke.bean.entity.$Entity$;
import com.jsrmos.sasuke.dao.provider.SqlBuilder;
import com.jsrmos.sasuke.bean.bo.EntityPage;

import static com.jrymos.common.util.CommonUtils.isEmpty;

public class $Entity$$method$SqlBuilder implements SqlBuilder {
    public String build(Object o) {
        String sql = null;
        EntityPage<$Entity$> entityPage = (EntityPage<$Entity$>) o;
        $Entity$ entity = entityPage.getEntity();
        StringBuilder conditionBuilder = new StringBuilder();
        String timeColumn = entityPage.isUseUpdateTime() ? "update_time" : "create_time";
        if (entityPage.getStartDate() != null) {
            conditionBuilder.append(" and ").append(timeColumn).append(" >= #{data.startDate}");
        }
        if (entityPage.getEndDate() != null) {
            conditionBuilder.append(" and ").append(timeColumn).append(" < #{data.endDate}");
        }
        // build sql
        %s
        return sql;
    }
}

