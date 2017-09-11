package com.topie.data.database.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topie.data.common.handler.Sortable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "d_notice")
public class Notice extends Sortable {

    private static final long serialVersionUID = -1220249619726982531L;

    /**
     * 公告ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型 0:社区 1：物业
     */
    private Integer type;

    /**
     * 位置 0：普通 1：轮播
     */
    private Integer position;

    /**
     * 标题
     */
    private String title;

    /**
     * 轮播图uri
     */
    @Column(name = "banner_uri")
    private String bannerUri;

    /**
     * 是否上线
     */
    @Column(name = "is_online")
    private Boolean isOnline;

    /**
     * 发布用户
     */
    @Column(name = "c_user")
    private String cUser;

    /**
     * 添加时间
     */
    @Column(name = "c_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;

    /**
     * 发布时间
     */
    @Column(name = "p_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pTime;

    /**
     * 内容
     */
    private String content;

    @Transient
    @JsonIgnore
    private String periodC;

    @Transient
    @JsonIgnore
    private String periodP;

    public String getPeriodC() {
        return periodC;
    }

    public void setPeriodC(String periodC) {
        this.periodC = periodC;
    }

    public String getPeriodP() {
        return periodP;
    }

    public void setPeriodP(String periodP) {
        this.periodP = periodP;
    }

    /**
     * 获取公告ID
     *
     * @return id - 公告ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置公告ID
     *
     * @param id 公告ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取类型 0:社区 1：物业
     *
     * @return type - 类型 0:社区 1：物业
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:社区 1：物业
     *
     * @param type 类型 0:社区 1：物业
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取位置 0：普通 1：轮播
     *
     * @return position - 位置 0：普通 1：轮播
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 设置位置 0：普通 1：轮播
     *
     * @param position 位置 0：普通 1：轮播
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取轮播图uri
     *
     * @return banner_uri - 轮播图uri
     */
    public String getBannerUri() {
        return bannerUri;
    }

    /**
     * 设置轮播图uri
     *
     * @param bannerUri 轮播图uri
     */
    public void setBannerUri(String bannerUri) {
        this.bannerUri = bannerUri;
    }

    /**
     * 获取是否上线
     *
     * @return is_online - 是否上线
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * 设置是否上线
     *
     * @param isOnline 是否上线
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * 获取发布用户
     *
     * @return c_user - 发布用户
     */
    public String getcUser() {
        return cUser;
    }

    /**
     * 设置发布用户
     *
     * @param cUser 发布用户
     */
    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    /**
     * 获取添加时间
     *
     * @return c_time - 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getcTime() {
        return cTime;
    }

    /**
     * 设置添加时间
     *
     * @param cTime 添加时间
     */
    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    /**
     * 获取发布时间
     *
     * @return p_time - 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getpTime() {
        return pTime;
    }

    /**
     * 设置发布时间
     *
     * @param pTime 发布时间
     */
    public void setpTime(Date pTime) {
        this.pTime = pTime;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}
