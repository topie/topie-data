;(function ($, window, document, undefined) {
    var mapping = {
        "/api/job/jobLog/list": "jobLog"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.jobLog = {
        page: function (title) {
            App.content.empty();
            App.title(title);
            var style = $('<style type="text/css">tr th{white-space: nowrap;text-align: left;} tr td{white-space: nowrap;text-align: left;}</style>');
            App.content.append(style);
            var content = $('<div class="panel-body" id="grid"></div>');
            App.content.append(content);
            initEvents();
        }
    };
    var initEvents = function () {
        var options = {
            url: App.href + "/api/job/jobLog/list",
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idField: "id",//id域指定
            headField: "id",
            showCheck: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: true,
            indexNumWidth: "5%",
            pageSelect: [5, 15, 30, 50],
            columns: [
                {
                    title: "唯一标示",
                    field: "jobKey",
                    format: function (i, d) {
                        return d.jobId + "_" + d.jobGroup;
                    }
                }, {
                    title: '调度时间',
                    field: 'triggerTime'
                }, {
                    title: '调度结果',
                    field: 'triggerCode',
                    tooltipHandle: function (i, d) {
                        return d.triggerMsg;
                    },
                    format: function (i, data) {
                        return (data.triggerCode === 200) ? '<span style="color: green">成功</span>' : (data.triggerCode === 500) ? '<span style="color: red">失败</span>' : (data.triggerCode === 0) ? '' : data.triggerCode;
                    }
                }, {
                    title: '执行时间',
                    field: 'handleTime'
                }, {
                    title: '执行结果',
                    field: 'handleCode',
                    format: function (i, data) {
                        return (data.handleCode === 200) ? '<span style="color: green">成功</span>' : (data.handleCode === 500) ? '<span style="color: red">失败</span>' : (data.handleCode === 0) ? '' : data.handleCode;
                    }
                }, {
                    title: '执行备注',
                    field: 'handleMsg'
                }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "20%",
            actionColumns: [
                {
                    text: '执行日志',
                    visible: function (i, d) {
                        return d.triggerCode === 200;
                    },
                    cls: 'btn-info btn-sm',
                    handle: function (index, data) {
                        var modal = $.orangeModal({
                            id: "edit_modal",
                            title: "编辑",
                            buttons: [
                                {
                                    type: 'button',
                                    text: '关闭',
                                    cls: "btn-default",
                                    handle: function (m) {
                                        m.hide()
                                    }
                                }
                            ],
                            destroy: true
                        }).show();
                        var section = $('<section class="content">' +
                            '            <pre style="font-size:12px;position:relative;" >' +
                            '                <div id="logConsole"></div>' +
                            '                <li class="fa fa-refresh fa-spin" style="font-size: 20px;float: left;" id="logConsoleRunning" ></li>' +
                            '                <div><hr><hr></div>' +
                            '            </pre>' +
                            '        </section>');
                        modal.$body.append(section);
                        $.ajax({
                            type: "GET",
                            dataType: "json",
                            data: {
                                id: data.id
                            },
                            url: App.href + "/api/job/jobLog/detail",
                            success: function (data) {
                                if (data.code === 200) {
                                    var jobLog = data.data;
                                    var logRun = setInterval(function () {
                                        pullLog()
                                    }, 3000);
                                    var logRunStop = function (content) {
                                        modal.$body.find('#logConsoleRunning').hide();
                                        logRun = window.clearInterval(logRun);
                                        modal.$body.find('#logConsole').append(content);
                                    };
                                    var pullLog = function () {
                                        if (pullFailCount++ > 20) {
                                            logRunStop('<span style="color: red;">终止请求Rolling日志,请求失败次数超上限,可刷新页面重新加载日志</span>');
                                            return;
                                        }
                                        $.ajax({
                                            type: 'POST',
                                            async: false,   // sync, make log ordered
                                            url: App.href + '/api/job/jobLog/lines',
                                            data: {
                                                "executorAddress": jobLog.executorAddress,
                                                "triggerTime": jobLog.triggerTime,
                                                "logId": jobLog.id,
                                                "fromLineNum": fromLineNum
                                            },
                                            dataType: "json",
                                            success: function (d) {
                                                if (d.code === 200) {
                                                    if (!d.content) {
                                                        console.log('pullLog fail');
                                                        return;
                                                    }
                                                    if (fromLineNum != d.content.fromLineNum) {
                                                        console.log('pullLog fromLineNum not match');
                                                        return;
                                                    }
                                                    if (fromLineNum > d.content.toLineNum) {
                                                        console.log('pullLog already line-end');

                                                        // valid end
                                                        if (d.content.end) {
                                                            logRunStop('<br><span style="color: green;">[Rolling Log Finish]</span>');
                                                            return;
                                                        }

                                                        return;
                                                    }

                                                    // append content
                                                    fromLineNum = d.content.toLineNum + 1;
                                                    modal.$body.find('#logConsole').append(d.content.logContent);
                                                    pullFailCount = 0;

                                                    // scroll to bottom
                                                    scrollTo(0, modal.$body.scrollHeight);        // $('#logConsolePre').scrollTop( document.body.scrollHeight + 300 );

                                                } else {
                                                    console.log('pullLog fail:' + data.msg);
                                                }
                                            }
                                        });
                                    };
                                    if (jobLog.triggerCode != 200) {
                                        modal.$body.find('#logConsoleRunning').hide();
                                        modal.$body.find('#logConsole').append('<span style="color: red;">任务发起调度失败，无法查看执行日志</span>');
                                        return;
                                    }
                                    var fromLineNum = 1;    // [from, to], start as 1
                                    var pullFailCount = 0;
                                    pullLog();
                                    if (jobLog.handleCode > 0) {
                                        logRunStop('<br><span style="color: green;">[Load Log Finish]</span>');
                                        return;
                                    }
                                } else {
                                    alert(data.message)
                                }
                            },
                            error: function (e) {
                                alert("请求异常。")
                            }
                        });
                    }
                }, {
                    text: '终止任务',
                    visible: function (i, d) {
                        return d.triggerCode === 200 && d.handleCode === 0;
                    },
                    cls: 'btn-danger btn-sm',
                    handle: function (index, data, grid) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/job/jobLog/kill";
                                $.ajax({
                                    type: "GET",
                                    dataType: "json",
                                    data: {
                                        id: data.id
                                    },
                                    url: requestUrl,
                                    success: function (data) {
                                        if (data.code === 200) {
                                            grid.reload()
                                        } else {
                                            alert(data.message)
                                        }
                                    },
                                    error: function (e) {
                                        alert("请求异常。")
                                    }
                                });
                            }
                        });
                    }
                }
            ],
            search: {
                rowEleNum: 2,
                //搜索栏元素
                items: [
                    {
                        type: 'select',
                        label: '执行器',
                        items: [
                            {
                                text: '全部',
                                value: ''
                            }
                        ]
                    }
                ]
            }
        };
        window.App.content.find("#grid").orangeGrid(options)
    };
})(jQuery, window, document);
