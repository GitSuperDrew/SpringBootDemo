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

</head>
<body>
<h1>hello world</h1>
<h1 style="color: red">${msg}</h1>

<h3>第一个table数据演示</h3>
<div style="width: 500px; margin: auto;">
    <table id="table_id_example" class="table table-hover">
        <thead>
        <tr>
            <th>姓名</th>
            <th>年龄</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>King</td>
            <td>20</td>
        </tr>
        <tr>
            <td>Hob</td>
            <td>22</td>
        </tr>
        <tr>
            <td>Ary</td>
            <td>22</td>
        </tr>
        <tr>
            <td>Nick</td>
            <td>22</td>
        </tr>
        <tr>
            <td>Jim</td>
            <td>22</td>
        </tr>
        <tr>
            <td>Drew</td>
            <td>22</td>
        </tr>
        <tr>
            <td>Bob</td>
            <td>23</td>
        </tr>
        <tr>
            <td>Bobe</td>
            <td>23</td>
        </tr>
        <tr>
            <td>Yellow</td>
            <td>33</td>
        </tr>
        <tr>
            <td>Red</td>
            <td>43</td>
        </tr>
        <tr>
            <td>Green</td>
            <td>13</td>
        </tr>
        <tr>
            <td>Chunck</td>
            <td>35</td>
        </tr>

        </tbody>
    </table>
</div>

<script>
    $('#table_id_example').DataTable({
        language: {
            url: '../DataTablesNoVersionSimpleDemo/locales/chinese-simple.json'  // 中国简体字（默认为 英文）
        }
    });
</script>
</body>
</html>