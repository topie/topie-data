/**
 * Created by chenguojun on 2017/2/10.
 */
(function ($, window, document, undefined) {
    var mapping = {
        "/api/core/dataJob/list": "dataJob"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.dataJob = {
        page: function (title) {
            window.App.content.empty();
            window.App.title(title);
            var content = $('<div class="panel-body" id="content"></div>');
            window.App.content.append(content);
            App.dataJob.initEvents();
        }
    };
    var formItems = [
        {
            type: 'hidden',
            name: 'id'
        }, {
            type: 'hidden',
            name: 'jobInfoId'
        }, {
            type: 'select',
            name: 'routerId',
            id: 'routerId',
            label: '数据路由',
            cls: 'input-large',
            items: [
                {
                    text: '请选择',
                    value: ''
                }
            ],
            itemsUrl: App.href + "/api/core/dataRouter/options",
            rule: {
                required: true
            },
            message: {
                required: "请选择数据路由"
            }
        },
        {
            type: 'select',
            name: 'jobGroupId',
            id: 'jobGroupId',
            label: '执行器',
            cls: 'input-large',
            items: [
                {
                    text: '请选择',
                    value: ''
                }
            ],
            itemsUrl: App.href + "/api/job/jobGroup/options",
            rule: {
                required: true
            },
            message: {
                required: "请选择执行器"
            }
        },
        {
            type: 'text',
            name: 'cron',
            id: 'cron',
            label: 'CRON任务调度周期',
            cls: 'input-large',
            rule: {
                required: true
            },
            message: {
                required: "CRON"
            }
        },
        {
            type: 'text',
            name: 'email',
            id: 'email',
            label: '报警邮箱',
            cls: 'input-large'
        }
    ];
    App.dataJob.initEvents = function () {
        var grid = {};
        var options = {
            url: App.href + "/api/core/dataJob/list",
            contentType: "table",
            showContentType: true,
            contentTypeItems: "table,card",
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idField: "id",//id域指定
            headField: "uri",
            showCheck: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: false,
            indexNumWidth: "5%",
            pageSelect: [2, 15, 30, 50],
            columns: [
                {
                    title: "描述",
                    field: "jobDesc"
                },
                {
                    title: "CRON调度周期",
                    field: "cron"
                }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "20%",
            actionColumns: [
                {
                    text: "编辑",
                    cls: "btn-info btn-sm",
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
                        var formOpts = {
                            id: "edit_form",
                            name: "edit_form",
                            method: "POST",
                            action: App.href + "/api/core/dataJob/update",
                            ajaxSubmit: true,
                            rowEleNum: 1,
                            ajaxSuccess: function () {
                                modal.hide();
                                grid.reload();
                            },
                            submitText: "提交",//保存按钮的文本
                            showReset: true,//是否显示重置按钮
                            resetText: "重置",//重置按钮文本
                            isValidate: true,//开启验证
                            buttons: [{
                                type: 'button',
                                text: '关闭',
                                handle: function () {
                                    modal.hide()
                                }
                            }],
                            buttonsAlign: "center",
                            items: formItems
                        };
                        var form = modal.$body.orangeForm(formOpts);
                        form.loadRemote(App.href + "/api/core/dataJob/load/" + data.id);
                    }
                },
                {
                    text: "开始",
                    cls: "btn-primary btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/core/dataJob/start";
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
                },
                {
                    text: "删除",
                    cls: "btn-danger btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/core/dataJob/delete";
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
            tools: [
                {
                    text: " 添 加",//按钮文本
                    cls: "btn btn-primary",//按钮样式
                    handle: function (grid) {
                        var modal = $.orangeModal({
                            id: "add_modal",
                            title: "添加",
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
                        var formOpts = {
                            id: "add_form",
                            name: "add_form",
                            method: "POST",
                            action: App.href + "/api/core/dataJob/insert",
                            ajaxSubmit: true,
                            rowEleNum: 1,
                            ajaxSuccess: function () {
                                modal.hide()
                                grid.reload()
                            },
                            submitText: "提交",//保存按钮的文本
                            showReset: true,//是否显示重置按钮
                            resetText: "重置",//重置按钮文本
                            isValidate: true,//开启验证
                            buttons: [{
                                type: 'button',
                                text: '关闭',
                                handle: function () {
                                    modal.hide()
                                }
                            }],
                            buttonsAlign: "center",
                            items: formItems
                        };
                        modal.$body.orangeForm(formOpts)
                    }
                }
            ],
            search: {
                rowEleNum: 2,
                //搜索栏元素
                items: [
                    {
                        type: "text",
                        label: "驱动名称",
                        name: "enName",
                        placeholder: "输入查询驱动名称"
                    }
                ]
            }
        };
        grid = App.content.find("#content").orangeGrid(options);
    }
})(jQuery, window, document);