<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>异步数据-freemarker</title>
    <!-- 第二种方式：CDN引入 -->
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.js"></script>
</head>
<body>

<!--  页面上的三个button，增删改 -->
<div class="btn-group operation">
    <button id="btn_add" type="button" class="btn bg-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
    </button>
    <button id="btn_edit" type="button" class="btn bg-info">
        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
    </button>
    <button id="btn_delete" type="button" class="btn btn-success">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
    </button>
</div>
<!-- Modal：添加图书 -->
<div class="modal fade" id="addBook" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">添加图书</h4>
            </div>
            <div id="addBookModal" class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for="bookName" class="col-sm-2 control-label">书名:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="bookName" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookAuthor" class="col-sm-2 control-label">作者:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="bookAuthor" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookPrice" class="col-sm-2 control-label">价格:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="bookPrice" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookPublish" class="col-sm-2 control-label">出版社:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="bookPublish" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookISBN" class="col-sm-2 control-label">ISBN:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="bookISBN" type="text">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="center-block">
                    <button id="cancelAdd" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="addBooksInfo" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal：修改图书 -->
<div class="modal fade" id="editBookInfo" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改图书内容</h4>
            </div>
            <div id="editBookModal" class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for="editBookName" class="col-sm-2 control-label">书名:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="editBookName" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editBookAuthor" class="col-sm-2 control-label">作者:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="editBookAuthor" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editBookPrice" class="col-sm-2 control-label">价格:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="editBookPrice" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editBookPublish" class="col-sm-2 control-label">出版社:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="editBookPublish" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editBookISBN" class="col-sm-2 control-label">ISBN:*</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="editBookISBN" type="text">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="center-block">
                    <button id="cancelEdit" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="saveEdit" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal：删除图书 -->
<div class="modal fade" id="deleteBook" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">确认要删除吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="delete" type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 表格实现 -->
<table id="books" class="table table-striped table-bordered row-border hover order-column" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>序号</th>
        <th>书名</th>
        <th>作者</th>
        <th>价格</th>
        <th>出版社</th>
        <th>ISBN</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>
</body>
<!-- JavaScript脚本 -->
<script>
    // 数据的获取支持三种：DOM、JavaScript本身的（以下就是）、Ajax请求后端得到。
    var data = [
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', '三体', '刘慈欣', '39.00', '重庆出版社', '982513213516'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'Linux基础教程', '鸟哥', '69.00', '清华大学出版社', '9787302510826'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415'],
        ['', 'MySQL DBA修炼之道', '陈晓勇', '79.00', '机械工业出版社', '9787111558415']]
    var titles = ['书名', '作者', '价格', '出版社', 'ISBN']
    $(function () {
        var table = $('#books').DataTable({
            data: data,
            "pagingType": "full_numbers",
            "bSort": true,
            // 国际化
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                // 排序方式
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "columnDefs": [{
                "searchable": false,
                "orderable": true,
                "targets": 0
            }],
            "order": [[1, 'asc']]
        });
        table.on('order.dt search.dt', function () {
            table.column(0, {
                search: 'applied',
                order: 'applied'
            }).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).draw();

        $('#books tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });
        // 取消添加
        $("#cancelAdd").on('click', function () {
            console.log('cancelAdd');
            $("#addBookModal").find('input').val('')
        })
        // 添加图书信息
        $("#addBooksInfo").on('click', function () {
            console.log('addBooksInfo');
            if (data.length) {
                if ($("#addBookModal").find('input').val() !== '') {
                    var bookName = $("#bookName").val()
                    var bookAuthor = $("#bookAuthor").val()
                    var bookPrice = $("#bookPrice").val()
                    var bookPublish = $("#bookPublish").val()
                    var bookISBN = $("#bookISBN").val()
                    var addBookInfos = [].concat(bookName, bookAuthor, bookPrice, bookPublish, bookISBN);
                    for (var i = 0; i < addBookInfos.length; i++) {
                        if (addBookInfos[i] === '') {
                            alert(titles[i] + '内容不能为空')
                        }
                    }
                    table.row.add(['', bookName, bookAuthor, bookPrice, bookPublish, bookISBN]).draw();
                    $("#addBookModal").find('input').val('')
                }
            } else {
                alert('请输入内容')
            }
        })
        $("#addBooksInfo").click();

        $("#btn_add").click(function () {
            console.log('add');
            $("#addBook").modal()
        });
        // 编辑图书
        $('#btn_edit').click(function () {
            console.log('edit');
            if (table.rows('.selected').data().length) {
                $("#editBookInfo").modal()
                var rowData = table.rows('.selected').data()[0];
                var inputs = $("#editBookModal").find('input')
                for (var i = 0; i < inputs.length; i++) {
                    $(inputs[i]).val(rowData[i + 1])
                }
            } else {
                alert('请选择项目');
            }
        });
        // 保存编辑
        $("#saveEdit").click(function () {
            var editBookName = $("#editBookName").val()
            var editBookAuthor = $("#editBookAuthor").val()
            var editBookPrice = $("#editBookPrice").val()
            var editBookPublish = $("#editBookPublish").val()
            var editBookISBN = $("#editBookISBN").val()
            var newRowData = [].concat(editBookName, editBookAuthor, editBookPrice, editBookPublish, editBookISBN);
            var tds = Array.prototype.slice.call($('.selected td'))
            for (var i = 0; i < newRowData.length; i++) {
                if (newRowData[i] !== '') {
                    tds[i + 1].innerHTML = newRowData[i];
                } else {
                    alert(titles[i] + '内容不能为空')
                }
            }
        })
        // 取消保存
        $("#cancelEdit").click(function () {
            console.log('cancelAdd');
            $("#editBookModal").find('input').val('')
        })
        // 删除项目
        $('#btn_delete').click(function () {
            if (table.rows('.selected').data().length) {
                $("#deleteBook").modal()
            } else {
                alert('请选择项目');
            }
        });
        // 删除
        $('#delete').click(function () {
            table.row('.selected').remove().draw(false);
        });

    })
</script>

</html>