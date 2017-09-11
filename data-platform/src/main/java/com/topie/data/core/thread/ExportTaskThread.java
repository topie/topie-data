package com.topie.data.core.thread;

import com.topie.data.core.service.IExportTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by chenguojun on 2017/4/19.
 */
public class ExportTaskThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ExportTaskThread.class);

    private IExportTaskService iExportTaskService;

    private Integer taskId;

    private String type;

    private Object param;

    private String path;

    public ExportTaskThread(IExportTaskService iExportTaskService, Integer taskId, String type, Object param,
            String path) {
        this.iExportTaskService = iExportTaskService;
        this.taskId = taskId;
        this.type = type;
        this.param = param;
        this.path = path;
    }

    @Override
    public void run() {
        logger.info("任务开始 [{}]", this.taskId);
        try {
            iExportTaskService.export(this.type, this.param,this.path);
        } catch (IOException e) {
            e.printStackTrace();
            iExportTaskService.updateToFail(this.taskId);
        }
        iExportTaskService.updateToDone(this.taskId);
        logger.info("任务结束 [{}]", this.taskId);
    }
}
