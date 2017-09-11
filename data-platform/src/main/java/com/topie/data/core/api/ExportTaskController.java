package com.topie.data.core.api;

import com.topie.data.common.utils.PageConvertUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.common.utils.Result;
import com.topie.data.core.service.IExportTaskService;
import com.topie.data.database.core.model.ExportTask;
import com.topie.data.security.utils.SecurityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/exportTask")
public class ExportTaskController {

    @Autowired
    private IExportTaskService iExportTaskService;

    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    @ResponseBody
    public Result myList(ExportTask exportTask,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        String userName = SecurityUtil.getCurrentUserName();
        exportTask.setExportUser(userName);
        PageInfo<ExportTask> pageInfo = iExportTaskService.selectByFilterAndPage(exportTask, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ExportTask exportTask,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ExportTask> pageInfo = iExportTaskService.selectByFilterAndPage(exportTask, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iExportTaskService.delete(id);
        return ResponseUtil.success();
    }

}
