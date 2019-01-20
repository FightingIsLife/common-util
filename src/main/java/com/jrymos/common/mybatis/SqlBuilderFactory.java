package com.jrymos.common.mybatis;

import com.google.common.base.Joiner;
import com.jrymos.common.DynamicSourceLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

import static com.jrymos.common.DynamicSourceLoad.newInstance;
import static com.jrymos.common.util.StringTransUtils.firstUppercase;


/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/31 12:18
 * @Description:
 */
public class SqlBuilderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlBuilderFactory.class);

    public static void destroy(){
        // 无用资源gc
        DynamicSourceLoad.baseSqlBuilderTemplate = null;
        DynamicSourceLoad.entityPageSqlBuilderTemplate = null;
    }

    public static SqlBuilder insertSqlBuilder(Class entityClass, String tableName, Map<String, Field> columnFieldMap) {
        StringBuilder dynamicCode = new StringBuilder();
        columnFieldMap.forEach((column, field) -> dynamicCode.append(format(column, field, "" +
                "       if (!isEmpty(entity.get$uField$())) {\n" +
                "         columnBuilder.append(\"$column$,\");\n" +
                "         valueBuilder.append(\"#{$lField$},\");\n" +
                "       }\n"
        )));
        String doSomething = "" +
                "$Entity$ entity = ($Entity$) o;\n" +
                "StringBuilder columnBuilder = new StringBuilder();\n" +
                "StringBuilder valueBuilder = new StringBuilder();\n" +
                dynamicCode +
                "columnBuilder.deleteCharAt(columnBuilder.lastIndexOf(\",\"));\n" +
                "valueBuilder.deleteCharAt(valueBuilder.lastIndexOf(\",\"));\n" +
                "sql= \"insert into $tableName$ (\"+columnBuilder+\") value(\"+valueBuilder+\")\";\n";
        return newSqlBuilder(entityClass, doSomething, tableName, "Insert");
    }

    public static SqlBuilder selectByIdSqlBuilder(Class entityClass, String tableName, Map<String, Field> columnFieldMap) {
        String columns = Joiner.on(",").join(columnFieldMap.keySet());
        String doSomething = "sql= \"select " + columns + " from $tableName$ where id = #{id}\";\n";
        return newSqlBuilder(entityClass, doSomething, tableName, "SelectById");
    }

    public static SqlBuilder updateSqlBuilder(Class entityClass, String tableName, Map<String, Field> columnFieldMap) {
        StringBuilder dynamicCode = new StringBuilder();
        columnFieldMap.forEach((column, field) -> {
            switch (column) {
                case "id":
                    break;
                case "update_time":
                    dynamicCode.append("columnBuilder.append(\"update_time=now(),\");\n");
                    break;
                default:
                    dynamicCode.append(format(column, field, "" +
                            "       if (!isEmpty(entity.get$uField$())) {\n" +
                            "           columnBuilder.append(\"$column$=#{$lField$},\");\n" +
                            "       }\n"
                    ));
                    break;
            }
        });
        String doSomething = "" +
                "$Entity$ entity = ($Entity$) o;\n" +
                "StringBuilder columnBuilder = new StringBuilder();\n" +
                dynamicCode +
                "columnBuilder.deleteCharAt(columnBuilder.lastIndexOf(\",\"));\n" +
                "sql= \"update $tableName$ set \" + columnBuilder + \" where id = #{id}\";\n";
        return newSqlBuilder(entityClass, doSomething, tableName, "Update");
    }


    public static SqlBuilder selectByEntityPage(Class entityClass, String tableName, Map<String, Field> columnFieldMap) {
        StringBuilder dynamicCode = new StringBuilder();
        columnFieldMap.forEach((column, field) -> dynamicCode.append(format(column, field, "" +
                "       if (!isEmpty(entity.get$uField$())) {\n" +
                "           conditionBuilder.append(\"$column$=#{data.entity.$lField$},\");\n" +
                "       }\n"
        )));
        String columns = Joiner.on(",").join(columnFieldMap.keySet());
        String doSomething = "" +
                "if(entity!=null){\n" + dynamicCode + "\n}\n" +
                "sql=\"select " + columns + " from $tableName$ where id>#{data.idOffset}\"+ conditionBuilder +\" limit #{data.pageSize}\";";
        return newSqlBuilder(DynamicSourceLoad.entityPageSqlBuilderTemplate, entityClass, doSomething, tableName, "SelectByEntityPage");
    }

    private static SqlBuilder newSqlBuilder(String sqlBuilderTemplate, Class entityClass, String doSomething, String tableName, String method) {
        String SUFFIX = "SqlBuilder";
        String className = entityClass.getSimpleName() + method + SUFFIX;
        String javaSource = String.format(sqlBuilderTemplate, doSomething)
                .replaceAll("\\$IMPORT_ENTITY\\$",entityClass.getName())
                .replaceAll("\\$Entity\\$", entityClass.getSimpleName())
                .replaceAll("\\$method\\$", method)
                .replaceAll("\\$tableName\\$", tableName);
        LOGGER.debug("className:{},javaSource:\n{}", className, javaSource);
        return newInstance(javaSource);
    }

    private static SqlBuilder newSqlBuilder(Class entityClass, String doSomething, String tableName, String method) {
        return newSqlBuilder(DynamicSourceLoad.baseSqlBuilderTemplate, entityClass, doSomething, tableName, method);
    }

    private static String format(String column, Field field, String dynamicCode) {
        return dynamicCode.replaceFirst("\\$uField\\$", firstUppercase(field.getName()))
                .replaceFirst("\\$lField\\$", field.getName())
                .replaceFirst("\\$column\\$", column);
    }
}
