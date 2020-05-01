<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/easyui/uimaker/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>
    <title>Role Page</title>

    <style>
        #role_dialog #roleForm .panel-header {
            height: 25px;
        }
        #role_dialog #roleForm .panel-title {
            color: black;
            margin-top: -5px;
            text-align: center;
        }

    </style>
</head>
<body>

<div id="role_datagrid"></div>

<%-- Toolbar--%>
<div id="role_datagrid_toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="role_add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="role_edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="role_delete">删除</a>
</div>

<%-- Dialog--%>
<div id="role_dialog">
    <form id="roleForm" method="post">
        <%-- hidden tag to store rid--%>
        <input type="hidden" name="rid">
        <table align="center" style="border-spacing: 0px 10px">
            <tr>
                <td>职能名称:<input type="text" name="rname" class="easyui-validatebox"></td>
                <td>职能编号:<input type="text" name="rnum" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td><div id="permissionAll_list"></div></td>
                <td><div id="permissionSelected_list"></div></td>
            </tr>
        </table>

    </form>
</div>

</body>
</html>
