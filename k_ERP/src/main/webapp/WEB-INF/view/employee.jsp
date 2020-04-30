<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/employee.js"></script>
    <title>Employee Page</title>
</head>
<body>

<%-- Toolbar--%>
<div id="datagrid_toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">删除</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
</div>

<%-- Datagrid --%>
<table id="datagrid"></table>


<%-- Dialog--%>
<div id="dialog">
    <form id="employeeForm" method="post">
        <%-- hidden tag to store id--%>
        <input type="hidden" name="id">
        <table align="center" style="border-spacing: 0px 10px">
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="username" class="easyui-validatebox"></td>
            </tr>
            <tr id="password">
                <td>密码:</td>
                <td><input type="text" name="password" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>手机:</td>
                <td><input type="text" name="tel" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="email" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>入职日期:</td>
                <td><input type="text" name="inputtime" class="easyui-datebox"></td>
            </tr>
            <tr>
                <td>部门:</td>
                <td><input id="department" name="department.id" placeholder="请选择部门"></input></td>
            </tr>
            <tr>
                <td>是否管理员:</td>
                <td><input id="state" name="admin" placeholder="请选择是否为管理员"></input></td>
            </tr>
        </table>

    </form>
</div>





</body>
</html>
