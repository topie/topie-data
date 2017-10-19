;(function ($, window, document, undefined) {
    var mapping = {
        "/api/job/jobInfo/list": "jobInfo"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.jobInfo = {
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
    var formItems = [

    ];
    var initEvents = function () {
        var options = {
            url: App.href + "/api/job/jobInfo/list",
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idField: "id",//id域指定
            headField: "id",
            showCheck: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: true,
            indexNumWidth: "5%",
            pageSelect: [5, 15, 30, 50],
            sort: 'addTime_desc',
            columns: [
                {
                    title: "唯一标示",
                    field: "jobKey",
                    format: function (i, d) {
                        return d.id + "_" + d.jobGroup;
                    }
                }, {
                    title: '运行模式',
                    field: 'glueType'
                }, {
                    title: 'CRON',
                    field: 'jobCron'
                }, {
                    title: '负责人',
                    field: 'author'
                }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "20%",
            actionColumns: [
                {
                    text: "删除",
                    cls: "btn-danger btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {

                            }
                        });
                    }
                }
            ],
            tools: [
                {
                    text: " 添 加",
                    cls: "btn btn-primary",
                    icon: "fa fa-plus",
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
                            action: App.href + "/api/core/dataRouter/insert",
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
                        modal.$body.orangeForm(formOpts);
                    }
                }
            ],
            search: {
                rowEleNum: 2,
                //搜索栏元素
                items: []
            }
        };
        window.App.content.find("#grid").orangeGrid(options)
    };
})(jQuery, window, document);
