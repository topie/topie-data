package com.topie.data.security.dto;

import java.io.Serializable;

/**
 * Created by chenguojun on 8/3/16.
 */
public class FunctionDTO implements Serializable {

    private static final long serialVersionUID = 1724750962448687444L;

    private Integer id;

    private Integer parentId;

    private String functionName;

    private String action;

    private String icon;

    private Integer functionDesc;

    public Integer getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(Integer functionDesc) {
        this.functionDesc = functionDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
