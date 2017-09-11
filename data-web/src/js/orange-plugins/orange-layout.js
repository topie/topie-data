/**
 * Created by chenguojun on 8/29/16.
 */

(function ($, window, document, undefined) {
    var Layout = function (element, options) {
        this._options = options;
        this.$element = $(element);
        var id = element.id;
        if (id === undefined) {
            id = "davdian_layout_" + new Date().getTime();
            this.$element.attr("id", id);
        }
        this._elementId = id;
    };
    Layout.defaults = {};
    Layout.prototype = {
        getPanel: function (title) {
            var panelTmpl =
                '<div class="panel panel-default" >' +
                '<div class="panel-heading">${titile_}</div>' +
                '<div class="panel-body"></div>' +
                '</div>';
            return $.tmpl(panelTmpl, {
                "title_": title
            });
        },
        render: function () {
            var that = this;
            if (this._options.rows !== undefined && this._options.rows.length > 0) {
                $.each(this._options.rows, function (i) {
                    var row = $(this);
                    var rowElement = $('<div class="row"></div>');
                    this.$element.append(rowElement);
                    if (row.spans !== undefined && row.spans.length > 0) {
                        $.each($(this).spans, function (j) {
                            var span = $(this);
                            var spanElement = $('<div class="col-md-' + span.span + ' col-sm-12"></div>');
                            rowElement.append(spanElement);
                            if (span.type === 'panel') {
                                var panel = getPanel(span.title);
                                that.renderContent(panel.find("div.panel-body"), span.content);
                            } else {
                                that.renderContent(spanElement, span.content);
                            }

                        });
                    }
                });
            }

        },
        renderContent: function (spanElement, content) {
            if (content.plugin !== undefined) {
                switch (content.plugin) {
                    case 'grid':
                        $(spanElement).orangeGrid(content.options);
                        break;
                    case 'form':
                        $(spanElement).orangeForm(content.options);
                        break;
                }
            }
        }
    };

    /**
     * jquery插件扩展 ===================================================
     */

    var getLayout = function (options) {
        options = $.extend(true, {}, Grid.defaults, options);
        var eles = [];
        this.each(function () {
            var self = this;
            var instance = new Layout(self, options);
            eles.push(instance);
        });
        return eles[0];
    };

    $.extend(
        {
            'orangeLayout': getLayout
        }
    );
})(jQuery, window, document);
