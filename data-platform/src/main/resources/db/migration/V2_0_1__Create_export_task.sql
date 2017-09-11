DROP TABLE IF EXISTS `d_export_task`;
CREATE TABLE `d_export_task` (
  `id`             INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '任务id',
  `export_user`    VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '导出用户',
  `export_time`    TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
  COMMENT '导出时间',
  `complete_time`  TIMESTAMP   NULL
  COMMENT '完成时间',
  `task_name`      VARCHAR(128)         DEFAULT ''
  COMMENT '任务名称',
  `attachment_uri` VARCHAR(255)         DEFAULT ''
  COMMENT '导出文件URI',
  `status`         SMALLINT             DEFAULT 1
  COMMENT '任务状态 1:进行中 2：已完成 3:已停止',
  PRIMARY KEY (`id`),
  KEY k_user(`export_user`)
)
  DEFAULT CHARSET = utf8
  COMMENT '导出任务表';
