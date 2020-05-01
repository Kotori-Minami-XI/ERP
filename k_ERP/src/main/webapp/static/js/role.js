$(function () {
    // Role data grid
    $('#role_datagrid').datagrid({
        url:"/getRolePage.action",
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
        window.behavior = "role_add";
        $('#role_dialog').dialog('open');
        $('#role_dialog').dialog('setTitle', "添加职能");

        // Reset all input tags
        $('#roleForm').form('clear');
        $('#permissionSelected_list').datagrid('loadData',{rows:[]});
        $('#permissionAll_list').datagrid('reload');
    });

    // Edit role
    $('#role_edit').click(function () {
        window.behavior = "role_edit";
        // Step 1: Get selected row
        var rowData = $("#role_datagrid").datagrid("getSelected");
        // console.log(rowData);
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }

        // Step 2: Open dialog and display current info
        $('#role_dialog').dialog('open');
        $('#role_dialog').dialog('setTitle', "编辑职能");

        $("#roleForm").form('load', rowData);

        // Step 3: clean data grid and reload list once again
        $('#permissionAll_list').datagrid('reload');
        $('#permissionSelected_list').datagrid('loadData',{rows:[]});

        // Step 4: Obtain and display current selected permissions from database
        $.get("/getCurrentPermissionByRid.action?rid=" + rowData.rid, function (data) {
            var allRows = $('#permissionAll_list').datagrid('getRows');
            // console.log(allRows);
            // console.log(data);
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < allRows.length; j++) {
                    if (allRows[j].pid == data[i].pid) {
                        $('#permissionAll_list').datagrid('selectRow', j);
                        break;
                    }
                }
            }
        });
    });

    // Delete role
    $('#role_delete').click(function () {
        var rowData = $("#role_datagrid").datagrid("getSelected");
        // console.log(rowData);
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }

        $.messager.confirm("确认", "确定删除职能吗",function (choice) {
            if (choice == true) {
                $.get("/deleteRole.action?rid=" + rowData.rid, function (data) {
                    if (data.result) {
                        $.messager.alert("提示",data.msg);
                        $("#role_datagrid").datagrid("reload");
                    } else {
                        $.messager.alert("提示",data.msg);
                    }
                });
            }
        });
    });

    // Role dialog
    $('#role_dialog').dialog({
        width: 600,
        height: 600,
        closed: true,
        modal: true,
        draggable: false,
        buttons:[{
            text:'保存',
            handler:function(){
                var url;
                if (window.behavior == "role_add") {
                    url = "/saveRole.action";
                } else if (window.behavior == "role_edit") {
                    url = "/updateRole.action";
                } else {
                    url = "/";
                }

                $('#roleForm').form("submit",{
                    url:url,
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