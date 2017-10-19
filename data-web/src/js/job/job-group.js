;(function ($, window, document, undefined) {
    var mapping = {
        "/api/job/jobGroup/list": "jobGroup"
    };
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    App.jobGroup = {
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
            url: App.href + "/api/job/jobGroup/list",
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
                    title: '客户端名称',
                    field: 'appName'
                }, {
                    title: '注册类型',
                    field: 'addressType',
                    format: function (i, d) {
                        return d.addressType === 0 ? '自动注册' : '手动录入';
                    }
                }, {
                    title: 'IP',
                    field: 'addressList'
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
                            destroy: true
                        }).show();
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
