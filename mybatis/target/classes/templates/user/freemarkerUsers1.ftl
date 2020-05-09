<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker-Show-Data</title>
    <!-- 第二种方式：CDN引入 -->
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.js"></script>
    <style>
        td.highlight {
            background-color: whitesmoke !important;
        }
    </style>
</head>
<body>
<#--<h3>显示所有的用户信息</h3>-->
<h5 style="color: dodgerblue; text-align: center">Freemarker的学习网址为：
    <a href="https://freemarker.apache.org/docs/ref_directive_list.html" target="_blank">FreeMarker-Manual</a>
</h5>
<h5 style="color: dodgerblue; text-align: center">DataTable的学习网址为：
    <a href="https://datatables.net/" target="_blank">DataTable-Manual</a>
</h5>
<#--<table>-->
<#--    <thead>-->
<#--    <tr>-->
<#--        <th>用户ID</th>-->
<#--        <th>姓名</th>-->
<#--        <th>密码</th>-->
<#--    </tr>-->
<#--    <#list users as u>-->
<#--        <tr>-->
<#--            <td>${u.id}</td>-->
<#--            <td>${u.name}</td>-->
<#--            <td>${u.password}</td>-->
<#--        </tr>-->
<#--    </#list>-->
<#--    </thead>-->
<#--</table>-->

<h3>异步请求数据</h3>
<hr>
<div style="font-size: large; color: black; margin-bottom: 10px;">
    Toggle column:
    <a class="toggle-vis" data-column="0">ID</a> -&nbsp;
    <a class="toggle-vis" data-column="1">姓名</a> -&nbsp;
    <a class="toggle-vis" data-column="2">别名</a>
</div>
<table id="example" class="display table table-striped table-bordered row-border hover order-column" cellspacing="0"
       style="width:100%">
    <thead>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>别名</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>别名</th>
    </tr>
    </tfoot>
</table>
<hr>
<script>
    $(function () {
        var table = $('#example').DataTable({
            "scrollY": "200px", // 固定表格的大小
            "paging": true, // 默认有分页效果
            "ajax": "${springMacroRequestContext.contextPath}/freemarkerForUser/findAll",
            language: {
                url: '../DataTablesNoVersionSimpleDemo/locales/Chinese-simple.json'  // 中国简体字（默认为 英文）
            },
            "columns": [
                {
                    "data": "id", render: function (data, type, full, meta) {
                        if (data <= 2) {
                            return data === 2 ? 2 : 1; // 修改原始数据再展示
                        } else {
                            return data;
                        }
                    }
                },
                {"data": "name"},
                {"data": "password"}
            ]
        });
        // 对鼠标悬浮的cell列高亮
        $('#example tbody').on('mouseenter', 'td', function () {
            var colIdx = table.cell(this).index().column;
            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
        });
        // 隐藏/显示 需要操作的列
        $('a.toggle-vis').on('click', function (e) {
            e.preventDefault();

            // Get the column API object
            var column = table.column($(this).attr('data-column'));

            // Toggle the visibility
            column.visible(!column.visible());
        });
    });
</script>

</body>
</html>