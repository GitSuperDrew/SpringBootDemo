<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker-Dome</title>

    <!-- 第一种方式：本地项目中引入 -->
    <script src="../DataTablesNoVersionSimpleDemo/js/jquery-3.4.1.min.js"></script>
    <!-- 引入boot strap.css -->
    <link rel="stylesheet" href="../DataTablesNoVersionSimpleDemo/css/bootstrap.min.css">
    <script src="../DataTablesNoVersionSimpleDemo/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="../DataTablesNoVersionSimpleDemo/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="../DataTablesNoVersionSimpleDemo/css/dataTableIndex.css">
    <style>
        td.highlight {
            background-color: wheat !important; /* whitesmoke */
        }
    </style>
</head>
<body>
<h3>异步请求数据</h3>
<hr>
<div style="font-size: large; color: black; margin-bottom: 10px;">
    Toggle column:
    <a class="toggle-vis" data-column="0">ID</a> -&nbsp;
    <a class="toggle-vis" data-column="1">姓名</a> -&nbsp;
    <a class="toggle-vis" data-column="3">职位</a> -
    <a class="toggle-vis" data-column="4">办公地点</a> -
    <a class="toggle-vis" data-column="5">入职日期</a> -
    <a class="toggle-vis" data-column="6">薪水</a>
</div>
<table id="example" class="display table table-striped table-bordered row-border hover order-column" cellspacing="0"
       style="width:100%">
    <thead>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>职位</th>
        <th>办公地点</th>
        <th>年龄</th>
        <th>入职日期</th>
        <th>薪水</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>职位</th>
        <th>办公地点</th>
        <th>年龄</th>
        <th>入职日期</th>
        <th>薪水</th>
    </tr>
    </tfoot>
</table>

</body>
<script>
    <#--    https://datatables.net/examples/server_side/row_details.html  -->
    $(function () {
        // alert(1232);
        var table = $('#example').DataTable({
            // "scrollY": "400px", // 固定表格的大小
            "paging": true, // 默认有分页效果
            searching: false, // 搜索失效
            ordering: true, // false 不排序
            "order": [[1, 'desc']],
            "ajax": "${springMacroRequestContext.contextPath}/freemarkerForSalary/allSalary",
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
                {"data": "position"},
                {"data": "office"},
                {"data": "age"},
                {
                    "data": "startDate", render(data, type, full, meta) {
                        return data;
                    }
                },
                {
                    "data": "salary", render(data, type, row) {
                        return '$' + data;
                    }
                }
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
    })
</script>
</html>