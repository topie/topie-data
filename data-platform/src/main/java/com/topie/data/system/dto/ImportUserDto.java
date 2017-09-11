package com.topie.data.system.dto;

import com.topie.data.common.tools.excel.ExcelCell;

/**
 * Created by chenguojun on 2017/5/16.
 */
public class ImportUserDto {

    @ExcelCell(index = 1, head = "姓名")
    private String displayName;

    @ExcelCell(index = 2, head = "职位")
    private String roleName;

    @ExcelCell(index = 3, head = "手机号码")
    private String contactPhone;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
