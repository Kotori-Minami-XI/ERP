$(function () {
    // Role data grid
    $('#role_datagrid').datagrid({
        url:"/getRoleList.action",
        columns:[[
            {field:'rnum',title:'职能编号',width:100,align:'center'},
            {field:'rname',title:'职能名称',width:100,align:'center'}
        ]],
        success:function(data) {
            console.log(data);
        },
        singleSelect: true,
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        toolbar: '#role_datagrid_toolbar'
    });

    // Add role
    $('#role_add').click(function () {
        $('#role_dialog').dialog('open');
        $('#role_dialog').dialog('setTitle', "添加职能");

        // Reset all input tags
        $('#roleForm').form('clear');
        $('#permissionSelected_list').datagrid('loadData',{rows:[]});
        $('#permissionAll_list').datagrid('reload');
    });

    // Role dialog
    $('#role_dialog').dialog({
        width: 600,
        height: 600,
        buttons:[{
            text:'保存',
            handler:function(){
                $('#roleForm').form("submit",{
                    url:"/saveRole.action",
                    // Extra params when submitting the form
                    onSubmit: function (param) {
                        // Obtain and iterate all roles and set them in extra params
                        var allRows = $('#permissionSelected_list').datagrid('getRows');
                        for (var i=0; i<allRows.length; i++) {
                            param["permissions[" + i + "].pid"] = allRows[i].pid;
                        }
                    },
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.result) {
                            $.messager.alert("提示",data.msg);
                            $("#role_dialog").dialog("close");
                            $("#role_datagrid").datagrid("reload");
                        } else {
                            $.messager.alert("提示",data.msg);
                        }
                    }
                });
            }}, {
            text:'关闭',
            handler:function(){
                $('#role_dialog').dialog('close');
            }
        }],
    });

    $('#permissionAll_list').datagrid({
        title:'所有权限',
        width:250,
        height:400,
        url: '/getPermissionList.action',
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'}
        ]],
        onSelect: function(rowIndex, rowData) {
            if (rowData) {
                $('#permissionAll_list').datagrid('deleteRow', rowIndex);
                $('#permissionSelected_list').datagrid('appendRow', rowData);
            }
        },
        fitColumns:true,
        singleSelect: true
    });

    $('#permissionSelected_list').datagrid({
        title:'已选权限',
        width:250,
        height:400,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        onSelect: function(rowIndex, rowData) {
            if (rowData) {
                $('#permissionSelected_list').datagrid('deleteRow', rowIndex);
                $('#permissionAll_list').datagrid('appendRow', rowData);
            }
        },
        fitColumns:true,
        singleSelect: true
    });


});