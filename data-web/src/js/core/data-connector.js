/**
 * Created by chenguojun on 2017/2/10.
 */
(function ($, window, document, undefined) {
    var mapping = {
        "/api/core/dataConnector/list": "dataConnector"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.dataConnector = {
        page: function (title) {
            window.App.content.empty();
            window.App.title(title);
            var content = $('<div class="panel-body" id="content"></div>');
            window.App.content.append(content);
            App.dataConnector.initEvents();
        }
    };
    var formItems = [
        {
            type: 'hidden',
            name: 'id',
            id: 'id'
        }, {
            type: 'text',
            name: 'name',
            id: 'name',
            label: '链接名称',
            cls: 'input-large',
            rule: {
                required: true
            },
            message: {
                required: "请输入链接名称"
            }
        }, {
            type: 'select',
            name: 'driverId',
            id: 'driverId',
            label: '驱动',
            cls: 'input-large',
            items: [
                {
                    text: '请选择',
                    value: ''
                }
            ],
            itemsUrl: App.href + "/api/core/dataDriver/options",
            rule: {
                required: true
            },
            message: {
                required: "请选择驱动"
            }
        }
    ];
    App.dataConnector.initEvents = function () {
        var grid = {};
        var options = {
            url: App.href + "/api/core/dataConnector/list",
            contentType: "table",
            showContentType: true,
            contentTypeItems: "table,card",
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idField: "id",//id域指定
            headField: "name",
            showCheck: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: false,
            indexNumWidth: "5%",
            pageSelect: [2, 15, 30, 50],
            columns: [
                {
                    title: "驱动类型",
                    field: "driverType",
                    format: function (i, d) {
                        return d.driverType === 0 ? '读源' : '写源';
                    }
                }, {
                    title: "链接名",
                    field: "name"
                }, {
                    title: "负责人",
                    field: "username"
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
                            action: App.href + "/api/core/dataConnector/update",
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
                        form.loadRemote(App.href + "/api/core/dataConnector/load/" + data.id);
                    }
                }, {
                    text: "配置",
                    cls: "btn-info btn-sm",
                    handle: function (index, d) {
                        var modal = $.orangeModal({
                            id: "config_modal",
                            title: "配置",
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
                        $.ajax({
                            type: "GET",
                            dataType: "json",
                            url: App.href + "/api/core/dataDriver/loadDriverJson?id=" + d.driverId,
                            success: function (data) {
                                if (data.code === 200) {
                                    var formItems = data.data;
                                    var items = [];
                                    $.each(formItems, function (ii, dd) {
                                        items.push(dd);
                                    });
                                    var form = modal.$body.orangeForm({
                                        id: "edit_form",
                                        name: "edit_form",
                                        method: "POST",
                                        action: App.href + "/api/core/dataConnector/updateConnectorJson",
                                        ajaxSubmit: true,
                                        ajaxSuccess: function () {
                                            modal.hide();
                                            grid.reload();
                                        },
                                        showReset: false,
                                        showSubmit: false,
                                        isValidate: true,
                                        buttons: [
                                            {
                                                type: 'button',
                                                text: '提交',
                                                cls: 'btn btn-primary',
                                                handle: function (form) {
                                                    var json = form.getJson();
                                                    $.ajax({
                                                        type: "POST",
                                                        dataType: "json",
                                                        data: {
                                                            id: d.id,
                                                            "json": JSON.stringify(json)
                                                        },
                                                        url: App.href + "/api/core/dataConnector/updateConnectorJson",
                                                        success: function (data) {
                                                            if (data.code === 200) {
                                                                modal.hide();
                                                                grid.reload();
                                                            } else {
                                                                alert(data.message)
                                                            }
                                                        },
                                                        error: function (e) {
                                                            alert("请求异常。")
                                                        }
                                                    });
                                                }
                                            }
                                        ],
                                        buttonsAlign: "center",
                                        items: items
                                    });
                                    form.loadRemote(App.href + "/api/core/dataConnector/loadConnectorJson/" + d.id)
                                } else {
                                    alert(data.message);
                                }
                            },
                            error: function (e) {
                                alert("请求异常。");
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
                                var requestUrl = App.href + "/api/core/dataConnector/delete";
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
                            action: App.href + "/api/core/dataConnector/insert",
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
                        label: "链接名",
                        name: "name",
                        placeholder: "输入查询链接名"
                    }
                ]
            }
        };
        grid = App.content.find("#content").orangeGrid(options);
    }
})(jQuery, window, document);