package com.topie.data.core.service;

import com.topie.data.common.baseservice.IService;
import com.topie.data.database.core.model.ExportTask;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IExportTaskService extends IService<ExportTask> {

    PageInfo<ExportTask> selectByFilterAndPage(ExportTask exportTask, int pageNum, int pageSize);

    List<ExportTask> selectByFilter(ExportTask exportTask);

    int updateToDone(Integer taskId);

    void export(String type, Object params, String path) throws IOException;

    int updateToFail(Integer taskId);

}
