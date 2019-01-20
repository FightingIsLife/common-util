//$Entity$$method$SqlBuilder
package com.jrymos.common.dynamic.source;

import $IMPORT_ENTITY$;
import com.jsrmos.common.mybatis.SqlBuilder;
import com.jsrmos.common.mybatis.bean.EntityPage;

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

