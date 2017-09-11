package com.topie.data.core.dto;

/**
 * Created by chenguojun on 2017/5/13.
 */
public class CommonQueryDto {

    private String tableAlias;

    private String sql;

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
