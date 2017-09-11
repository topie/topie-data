package com.topie.data.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.data.common.baseservice.IService;
import com.topie.data.database.core.model.Notice;

import java.util.List;

/**
 * Created by chenguojun on 2017/6/27.
 */
public interface INoticeService extends IService<Notice> {

    PageInfo<Notice> selectByFilterAndPage(Notice notice, int pageNum, int pageSize);

    List<Notice> selectByFilter(Notice notice);
}
