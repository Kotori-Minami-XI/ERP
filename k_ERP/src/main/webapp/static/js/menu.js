$(function () {

    $("#menu_datagrid").datagrid({
        url:"/getMenuPage.action",
        columns:[[
            {field:'text', title:'名称', width:1,align:'center'},
            {field:'url', title:'跳转地址', width:1,align:'center'},
            {field:'parent', title:'父菜单', width:1,align:'center',formatter:function(value,row,index){
                    return value?value.text:'';
                }
            }
        ]],
        fit:true,
        rownumbers:true,
        singleSelect:true,
        striped:true,
        pagination:true,
        fitColumns:true,
        toolbar:'#menu_toolbar'
    });

    $("#menu_dialog").dialog({
        width:300,
        height:300,
        closed:true,
        buttons:'#menu_dialog_bt'
    });

    $("#parentMenu").combobox({
            width:160,
            panelHeight:'auto',
            editable:false,
            url:'/getParentList.action',
            textField:'text',
            valueField:'id',
            onLoadSuccess:function () {
                /* Callback function to reload placeholder*/
                $("#parentMenu").each(function(i){
                    var span = $(this).siblings("span")[i];
                    var targetInput = $(span).find("input:first");
                    if(targetInput){
                        $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                    }
                });
            }
    });

    // Add button
    $("#add").click(function () {
        window.behavior = "add_behavior";
        $("#menuForm").form("clear");
        $("#menu_dialog").dialog("setTitle","添加菜单");
        $("#menu_dialog").dialog("open");
    });

    // Edit button
    $("#edit").click(function () {
        $("#menuForm").form("clear");
        window.behavior = "edit_behavior";
        var rowData = $("#menu_datagrid").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }

        if (rowData.parent) {
            rowData["parent.id"] = rowData["parent"].id;
        } else {
            /* Callback function to reload placeholder*/
            var span = $(this).siblings("span")[i];
            var targetInput = $(span).find("input:first");
            if(targetInput){
                $(targetInput).attr("placeholder", $(this).attr("placeholder"));
            }
        }

        $("#menu_dialog").dialog("setTitle","编辑菜单");
        $("#menu_dialog").dialog("open");

        $("#menuForm").form("load",rowData);
    });

    // Delete button
    $("#delete").click(function () {
        var rowData = $("#menu_datagrid").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行删除");
            return;
        }

        $.messager.confirm("确认", "确定删除菜单吗",function (choice) {
            if (choice == true) {
                $.get("/deleteMenuById.action?id="+rowData.id,function (data) {
                    if (data.result) {
                        $.messager.alert("提示",data.msg);
                        $("#menu_datagrid").datagrid("reload");
                        $("#parentMenu").combobox("reload");
                    } else {
                        $.messager.alert("提示",data.msg);
                    }
                });
            }
        });
    });

    // Save button
    $("#save").click(function () {
        var behavior = window.behavior;
        var url;
        if (behavior == "edit_behavior") {
            var id = $("[name = 'id']").val();
            var parentid = $("[name = 'parent.id']").val();
            if (id == parentid) {
                $.messager.alert("提示","不能选择自己作为父菜单");
                return;
            }
            url = "/updateMenu.action"
        } else if (behavior == "add_behavior") {
            url = "/saveMenu.action"
        } else {
            url = "/"
        }

        /* Submit form */
        $("#menuForm").form("submit",{
            url: url,
            success:function (data) {
                data = $.parseJSON(data);
                if (data.result) {
                    $.messager.alert("提示",data.msg);
                    $("#menu_dialog").dialog("close");
                    $("#menu_datagrid").datagrid("reload");
                    $("#parentMenu").combobox("reload");
                } else {
                    $.messager.alert("提示",data.msg);
                }
            }
        });
    });

    $("#cancel").click(function () {
        $("#menu_dialog").dialog("close");
    });

});