package com.topie.data.database.core.model;

import com.topie.data.common.handler.Sortable;
import com.topie.data.common.utils.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "d_export_task")
public class ExportTask extends Sortable {

    private static final long serialVersionUID = 5241212753354470004L;

    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 导出用户
     */
    @Column(name = "export_user")
    private String exportUser;

    /**
     * 导出时间
     */
    @Column(name = "export_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exportTime;

    @Column(name = "complete_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 导出文件URI
     */
    @Column(name = "attachment_uri")
    private String attachmentUri;

    /**
     * 任务状态 1:进行中 2：已完成 3:已停止
     */
    private Integer status;

    @Transient
    @JsonIgnore
    private String period;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 获取任务id
     *
     * @return id - 任务id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置任务id
     *
     * @param id 任务id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取导出用户
     *
     * @return export_user - 导出用户
     */
    public String getExportUser() {
        return exportUser;
    }

    /**
     * 设置导出用户
     *
     * @param exportUser 导出用户
     */
    public void setExportUser(String exportUser) {
        this.exportUser = exportUser;
    }

    /**
     * 获取导出时间
     *
     * @return export_time - 导出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getExportTime() {
        return exportTime;
    }

    /**
     * 设置导出时间
     *
     * @param exportTime 导出时间
     */
    public void setExportTime(Date exportTime) {
        this.exportTime = exportTime;
    }

    /**
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取导出文件URI
     *
     * @return attachment_uri - 导出文件URI
     */
    public String getAttachmentUri() {
        return attachmentUri;
    }

    /**
     * 设置导出文件URI
     *
     * @param attachmentUri 导出文件URI
     */
    public void setAttachmentUri(String attachmentUri) {
        this.attachmentUri = attachmentUri;
    }

    /**
     * 获取任务状态 1:进行中 2：已完成 3:已停止
     *
     * @return status - 任务状态 1:进行中 2：已完成 3:已停止
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置任务状态 1:进行中 2：已完成 3:已停止
     *
     * @param status 任务状态 1:进行中 2：已完成 3:已停止
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCostTime() {
        if (getCompleteTime() != null) {
           return DateUtil.getTimeLengthBetweenSeconds(
                    (int) (getCompleteTime().getTime() - getExportTime().getTime()) / 1000);
        }
        return "-";
    }
}
