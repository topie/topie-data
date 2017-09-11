package com.topie.data.database.core.model;

import javax.persistence.*;

@Table(name = "d_common_query")
public class CommonQuery {

    /**
     * sqlId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 查询名称
     */
    @Column(name = "query_name")
    private String queryName;

    /**
     * 表别名
     */
    @Column(name = "table_alias")
    private String tableAlias;

    /**
     * 选择
     */
    @Column(name = "select_query")
    private String selectQuery;

    /**
     * 导出
     */
    @Column(name = "export_query")
    private String exportQuery;

    /**
     * 参数
     */
    @Column(name = "where_query")
    private String whereQuery;

    @Column(name = "group_query")
    private String groupQuery;

    /**
     * 排序
     */
    @Column(name = "order_query")
    private String orderQuery;

    /**
     * javascript脚本
     */
    @Column(name = "script_content")
    private String scriptContent;

    /**
     * html脚本
     */
    @Column(name = "html_content")
    private String htmlContent;

    public String getGroupQuery() {
        return groupQuery;
    }

    public void setGroupQuery(String groupQuery) {
        this.groupQuery = groupQuery;
    }

    public String getExportQuery() {
        return exportQuery;
    }

    public void setExportQuery(String exportQuery) {
        this.exportQuery = exportQuery;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * 获取sqlId
     *
     * @return id - sqlId
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置sqlId
     *
     * @param id sqlId
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取表别名
     *
     * @return table_alias - 表别名
     */
    public String getTableAlias() {
        return tableAlias;
    }

    /**
     * 设置表别名
     *
     * @param tableAlias 表别名
     */
    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    /**
     * 获取选择
     *
     * @return select_query - 选择
     */
    public String getSelectQuery() {
        return selectQuery;
    }

    /**
     * 设置选择
     *
     * @param selectQuery 选择
     */
    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    /**
     * 获取参数
     *
     * @return where_query - 参数
     */
    public String getWhereQuery() {
        return whereQuery;
    }

    /**
     * 设置参数
     *
     * @param whereQuery 参数
     */
    public void setWhereQuery(String whereQuery) {
        this.whereQuery = whereQuery;
    }

    /**
     * 获取排序
     *
     * @return order_query - 排序
     */
    public String getOrderQuery() {
        return orderQuery;
    }

    /**
     * 设置排序
     *
     * @param orderQuery 排序
     */
    public void setOrderQuery(String orderQuery) {
        this.orderQuery = orderQuery;
    }

    /**
     * 获取javascript脚本
     *
     * @return script_content - javascript脚本
     */
    public String getScriptContent() {
        return scriptContent;
    }

    /**
     * 设置javascript脚本
     *
     * @param scriptContent javascript脚本
     */
    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    /**
     * 获取html脚本
     *
     * @return html_content - html脚本
     */
    public String getHtmlContent() {
        return htmlContent;
    }

    /**
     * 设置html脚本
     *
     * @param htmlContent html脚本
     */
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
