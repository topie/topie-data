;(function ($, window, document, undefined) {
    var mapping = {
        "/api/core/notice/list": "notice"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.notice = {
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
        var grid = {};
        var options = {
            url: App.href + "/api/core/notice/list",
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idField: "id",//id域指定
            headField: "title",
            showCheck: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: true,
            indexNumWidth: "5%",
            pageSelect: [5, 15, 30, 50],
            sort: 'pTime_desc',
            columns: [
                {
                    title: "标题",
                    field: "title"
                }, {
                    title: '发布者',
                    field: 'cUser'
                }, {
                    title: '发布时间',
                    field: 'pTime'
                }, {
                    title: '类型',
                    field: 'type',
                    format: function (i, d) {
                        if (d.type == 1) {
                            return '物业公告';
                        } else {
                            return '社区公告';
                        }
                    }
                }, {
                    title: '是否上线',
                    field: 'isOnline',
                    format: function (i, d) {
                        if (d.isOnline == 1) {
                            return '已上线';
                        } else {
                            return '已下线';
                        }
                    }
                }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "20%",
            actionColumns: [
                {
                    text: "预览",
                    cls: "btn-primary btn-sm",
                    handle: function (index, data) {
                        var modal = $.orangeModal({
                            id: "notice_edit_modal",
                            title: data.title,
                            width: 900,
                            height: 500,
                            destroy: true,
                            buttons: [
                                {
                                    type: 'button',
                                    text: '关闭',
                                    cls: "btn-default",
                                    handle: function () {
                                        modal.hide()
                                    }
                                }
                            ]
                        }).show();
                        var formOpts = {
                            id: "notice_form",
                            name: "notice_form",
                            method: "POST",
                            labelInline: false,
                            action: App.href + "/api/core/notice/update",
                            ajaxSubmit: true,//是否使用ajax提交表单
                            ajaxSuccess: function () {
                                modal.hide();
                                grid.reload();
                            },
                            submitText: "保存",//保存按钮的文本
                            showReset: false,//是否显示重置按钮
                            showSubmit: false,
                            resetText: "重置",//重置按钮文本
                            isValidate: true,//开启验证
                            buttonsAlign: "center",
                            items: [
                                {
                                    type: 'display',
                                    name: 'title',
                                    id: 'title',
                                    style: 'text-align: center;',
                                    label: '',
                                    format: function (val) {
                                        return '<h1>' + val + '</h1>'
                                    }
                                },
                                {
                                    type: 'display',
                                    name: 'cUser',
                                    id: 'cUser',
                                    style: 'text-align: right;',
                                    label: '',
                                    format: function (val) {
                                        return '<h3>发布者:' + val + '</h3>'
                                    }
                                },
                                {
                                    type: 'display',
                                    name: 'bannerUri',
                                    id: 'bannerUri',
                                    style: 'text-align: center;',
                                    label: '',
                                    format: function (val) {
                                        return '<img src="' + val + '"/>'
                                    }
                                },
                                {
                                    type: 'display',
                                    name: 'content',
                                    id: 'content',
                                    label: ''
                                }
                            ]
                        };
                        var form = modal.$body.orangeForm(formOpts);
                        form.loadRemote(App.href + "/api/core/notice/load/" + data.id)
                    }
                }, {
                    text: "编辑",
                    cls: "btn-primary btn-sm",
                    handle: function (index, data) {
                        var modal = $.orangeModal({
                            id: "edit_modal",
                            title: "编辑",
                            destroy: true
                        }).show();
                        var form = modal.$body.orangeForm({
                            id: "edit_form",
                            name: "edit_form",
                            method: "POST",
                            action: App.href + "/api/core/notice/update",
                            ajaxSubmit: true,//是否使用ajax提交表单
                            ajaxSuccess: function () {
                                modal.hide();
                                grid.reload();
                            },
                            submitText: "保存",//保存按钮的文本
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
                            items: [
                                {
                                    type: 'hidden',
                                    name: 'id',
                                    id: 'id'
                                }, {
                                    type: 'text',
                                    name: 'title',
                                    id: 'title',
                                    label: '标题',
                                    cls: 'input-large',
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请输入标题"
                                    }
                                }, {
                                    type: 'select',
                                    name: 'type',
                                    id: 'type',
                                    tips: '有啥区别呢？我也不知道！',
                                    label: '公告类型',
                                    items: [
                                        {
                                            text: '普通公告',
                                            value: 0
                                        },
                                        {
                                            text: '首页公告',
                                            value: 1
                                        }
                                    ],
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请选择公告类型"
                                    }
                                }, {
                                    type: 'select',
                                    name: 'position',
                                    id: 'position',
                                    label: '公告位置',
                                    items: [
                                        {
                                            text: '普通',
                                            value: 0
                                        },
                                        {
                                            text: '轮播',
                                            value: 1
                                        }
                                    ],
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请选择公告位置"
                                    }
                                }, {
                                    type: 'file',
                                    id: 'bannerUri',
                                    name: 'bannerUri',
                                    label: '轮播图Uri',
                                    isAjaxUpload: true,
                                    onSuccess: function (data) {
                                        $("#bannerUri").attr("value", data.attachmentUrl);
                                    },
                                    deleteHandle: function () {
                                        $("#bannerUri").attr("value", "");
                                    },
                                    allowTypes: ".jpg,.png,.bmp"
                                }, {
                                    type: 'kindEditor',
                                    name: 'content',
                                    id: 'content',
                                    label: '正文',
                                    height: "300px",
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "正文"
                                    }
                                }, {
                                    type: 'text',
                                    name: 'cUser',
                                    id: 'cUser',
                                    label: '发布者'
                                }
                            ]
                        });
                        form.loadRemote(App.href + "/api/core/notice/load/" + data.id);
                    }
                }, {
                    text: "上线",
                    visible: function (i, d) {
                        return d.isOnline == 0;
                    },
                    cls: "btn-info btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/core/notice/updateToOnline";
                                $.ajax({
                                    type: "POST",
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
                }, {
                    text: "下线",
                    visible: function (i, d) {
                        return d.isOnline == 1;
                    },
                    cls: "btn-warning btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/core/notice/updateToOffline";
                                $.ajax({
                                    type: "POST",
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
                }, {
                    text: "删除",
                    cls: "btn-danger btn-sm",
                    handle: function (index, data) {
                        bootbox.confirm("确定该操作?", function (result) {
                            if (result) {
                                var requestUrl = App.href + "/api/core/notice/delete";
                                $.ajax({
                                    type: "POST",
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
                    text: " 添 加",
                    cls: "btn btn-primary",
                    icon: "fa fa-plus",
                    handle: function (grid) {
                        var modal = $.orangeModal({
                            id: "add_modal",
                            title: "添加",
                            destroy: true
                        }).show();
                        modal.$body.orangeForm({
                            id: "add_form",
                            name: "add_form",
                            method: "POST",
                            action: App.href + "/api/core/notice/insert",
                            ajaxSubmit: true,
                            rowEleNum: 1,
                            ajaxSuccess: function () {
                                modal.hide();
                                grid.reload();
                            },
                            submitText: "保存",
                            showReset: true,
                            resetText: "重置",
                            isValidate: true,
                            buttons: [
                                {
                                    type: 'button',
                                    text: '关闭',
                                    handle: function () {
                                        modal.hide()
                                    }
                                }
                            ],
                            buttonsAlign: "center",
                            items: [
                                {
                                    type: 'text',
                                    name: 'title',
                                    id: 'title',
                                    label: '标题',
                                    cls: 'input-large',
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请输入标题"
                                    }
                                }, {
                                    type: 'select',
                                    name: 'type',
                                    id: 'type',
                                    label: '公告类型',
                                    tips: '有啥区别呢？我也不知道！',
                                    items: [
                                        {
                                            text: '公告类型1',
                                            value: 0
                                        },
                                        {
                                            text: '公告类型2',
                                            value: 1
                                        }
                                    ],
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请选择公告类型"
                                    }
                                }, {
                                    type: 'select',
                                    name: 'position',
                                    id: 'position',
                                    label: '公告位置',
                                    items: [
                                        {
                                            text: '普通',
                                            value: 0
                                        },
                                        {
                                            text: '轮播',
                                            value: 1
                                        }
                                    ],
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "请选择公告位置"
                                    }
                                }, {
                                    type: 'image',
                                    id: 'bannerUri',
                                    name: 'bannerUri',
                                    label: '轮播图Uri',
                                    isAjaxUpload: true,
                                    onSuccess: function (data) {
                                        $("#bannerUri").attr("value", data.attachmentUrl);
                                    },
                                    deleteHandle: function () {
                                        $("#bannerUri").attr("value", "");
                                    }
                                }, {
                                    type: 'kindEditor',
                                    name: 'content',
                                    id: 'content',
                                    label: '正文',
                                    height: "300px",
                                    rule: {
                                        required: true
                                    },
                                    message: {
                                        required: "正文"
                                    }
                                }, {
                                    type: 'text',
                                    name: 'cUser',
                                    id: 'cUser',
                                    label: '发布者'
                                }
                            ]
                        })
                    }
                }
            ],
            search: {
                rowEleNum: 2,
                //搜索栏元素
                items: [
                    {
                        type: "select",
                        label: "公告类型",
                        name: "type",
                        items: [
                            {
                                text: '全部',
                                value: ''
                            },
                            {
                                text: '社区公告',
                                value: 0
                            },
                            {
                                text: '物业公告',
                                value: 1
                            }
                        ]
                    },
                    {
                        type: "select",
                        label: "状态",
                        name: "isOnline",
                        items: [
                            {
                                text: '全部',
                                value: ''
                            },
                            {
                                text: '已上线',
                                value: 1
                            },
                            {
                                text: '已下线',
                                value: 0
                            }
                        ]
                    }
                ]
            }
        };
        grid = window.App.content.find("#grid").orangeGrid(options)
    };
})(jQuery, window, document);
