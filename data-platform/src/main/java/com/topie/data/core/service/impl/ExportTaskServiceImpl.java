package com.topie.data.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.data.common.baseservice.impl.BaseService;
import com.topie.data.core.dto.CommonQueryDto;
import com.topie.data.core.service.ICommonQueryService;
import com.topie.data.core.service.IExportTaskService;
import com.topie.data.database.core.model.ExportTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ExportTaskServiceImpl extends BaseService<ExportTask> implements IExportTaskService {

    @Value("${upload.folder}")
    private String uploadFolder;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Override
    public PageInfo<ExportTask> selectByFilterAndPage(ExportTask exportTask, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExportTask> list = selectByFilter(exportTask);
        return new PageInfo<>(list);
    }

    @Override
    public List<ExportTask> selectByFilter(ExportTask exportTask) {
        Example example = new Example(ExportTask.class);
        Example.Criteria criteria = example.createCriteria();
        if (exportTask.getStatus() != null) criteria.andEqualTo("status", exportTask.getStatus());
        if (StringUtils.isNotEmpty(exportTask.getExportUser()))
            criteria.andEqualTo("exportUser", exportTask.getExportUser());
        if (StringUtils.isNotEmpty(exportTask.getPeriod())) {
            String[] dateArr = exportTask.getPeriod().split(" 到 ");
            if (StringUtils.isNotEmpty(dateArr[0])) {
                criteria.andGreaterThanOrEqualTo("tjDate", dateArr[0].trim());
            }
            if (StringUtils.isNotEmpty(dateArr[1])) {
                criteria.andLessThanOrEqualTo("tjDate", dateArr[1].trim());
            }
        }
        if (StringUtils.isNotEmpty(exportTask.getSortWithOutOrderBy()))
            example.setOrderByClause(exportTask.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

    @Override
    public int updateToDone(Integer taskId) {
        ExportTask task = selectByKey(taskId);
        task.setCompleteTime(new Date());
        if (task != null) {
            task.setStatus(2);
            updateNotNull(task);
            return 1;
        }
        return 0;
    }

    @Override
    public void export(String type, Object params, String path) throws IOException {
        switch (type) {
            case "dvd_table_alias":
                iCommonQueryService.export((CommonQueryDto) params, path);
                break;

        }
    }

    @Override
    public int updateToFail(Integer taskId) {
        ExportTask task = selectByKey(taskId);
        task.setCompleteTime(new Date());
        if (task != null) {
            task.setStatus(3);
            updateNotNull(task);
            return 1;
        }
        return 0;
    }
}
