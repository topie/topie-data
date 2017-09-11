package com.topie.data.common.handler;

import com.topie.data.common.utils.CamelUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by chenguojun on 8/31/16.
 */
public class Sortable implements Serializable {

    private static final long serialVersionUID = 4103411269761135435L;

    @Transient
    @JsonIgnore
    private String sort_;

    @Transient
    @JsonIgnore
    private Integer pageOffset;

    @Transient
    @JsonIgnore

    private Integer pageSize;

    public String getSort_() {
        if (StringUtils.isNotEmpty(getSortWithOutOrderBy())) return "order by " + getSortWithOutOrderBy();
        return "";
    }

    public void setSort_(String sort_) {
        this.sort_ = sort_;
    }

    public String getSortWithOutOrderBy() {
        String sortStr = "";
        if (!StringUtils.isEmpty(sort_)) {
            if (sort_.indexOf("_desc") != -1) {
                String filed = CamelUtil.camelToUnderline(sort_.replace("_desc", ""));
                sortStr = filed + " desc";
            } else if (sort_.indexOf("_asc") != -1) {
                String filed = CamelUtil.camelToUnderline(sort_.replace("_asc", ""));
                sortStr = filed + " asc";
            }
        }
        return sortStr;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
