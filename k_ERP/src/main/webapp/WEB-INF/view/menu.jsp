<%--
  Created by IntelliJ IDEA.
  User: Kotori
  Date: 2020/4/29
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu.js"></script>
    <title>Menu Page</title>
</head>
<body>

<!-- Menu data grid -->
<table id="menu_datagrid">
    <thead>
    <tr>
        <th>名称</th>
        <th>url</th>
        <th>父菜单</th>
    </tr>
    </thead>
</table>

<!-- Menu toolbar -->
<div id="menu_toolbar">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" id="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="delete">刪除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="reload">刷新</a>
    </div>
</div>

<!-- Menu dialog -->
<div id="menu_dialog">
    <form id="menuForm" method="post">
        <table align="center" style="margin-top: 15px;">
            <input type="hidden" name="id">
            <tr>
                <td>名称</td>
                <td><input type="text" name="text"></td>
            </tr>
            <tr>
                <td>url</td>
                <td><input type="text" name="url"></td>
            </tr>
            <tr>
                <td>父菜单</td>
                <td><input type="text" id="parentMenu" name="parent.id" class="easyui-combobox" placeholder="请选择父菜单"/></td>
            </tr>
        </table>
    </form>
</div>

<!-- Menu dialog button -->
<div id="menu_dialog_bt">
    <a class="easyui-linkbutton" id="save" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" id="cancel" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

</body>
</html>
