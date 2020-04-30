$(function () {

    // Data grid
    $('#datagrid').datagrid({
        url:"/getEmployeeList.action",
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'inputtime',title:'入职时间',width:100,align:'center'},
            {field:'tel',title:'电话',width:100,align:'center'},
            {field:'email',title:'邮箱',width:100,align:'center'},
            {field:'department',title:'部门',width:100,align:'center', formatter: function (value,row,index) {
                if (value.name) {
                    return value.name;
                }
            }},
            {field:'state',title:'状态',width:100,align:'center', formatter: function (value,row,index) {
                    return row.state ? "在职" : "<p style='color: red'>" + "离职</p>";
            }},
            {field:'admin',title:'管理员',width:100,align:'center', formatter: function (value,row,index) {
                        return row.admin ? "是" : "否";
            }}
        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        toolbar: '#datagrid_toolbar'
    });

    // Add button
    $('#add').click(function () {
        $("#dialog").dialog("clear");
        $("#dialog").dialog('open');
    });

    /* Department combobox */
    $("#department").combobox({
        width:160,
        panelHeight:'auto',
        editable:false,
        url:'/getDepartmentList.action',
        textField:'name',
        valueField:'id',
        onLoadSuccess:function () {
            /* Callback function to reload placeholder*/
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /* Admin combobox */
    $("#state").combobox({
        width:160,
        panelHeight:'auto',
        textField:'label',
        valueField:'value',
        editable:false,
        data:[{label:'是', value:'true'},
              {label:'否', value:'false'}],
        onLoadSuccess:function () {
            /* Callback function to reload placeholder*/
            $("#state").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }

    });

    /* Dialog */
    $("#dialog").dialog({
        width:350,
        height:400,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){
                /* Distinct edit or add operation */
                var id = $("[name='id']").val();
                var url;
                if(id){
                    /* Edit operation */
                    url = "/updateEmployee.action";
                }else {
                    /* Add operation */
                    url= "/saveEmployee.action";
                }
                url= "/saveEmployee.action";
                /* Submit form */
                $("#employeeForm").form("submit",{
                    url: "/saveEmployee.action",
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.result) {
                            $.messager("提示",data.msg);
                            $("#dialog").dialog("close");
                            $("#datagrid").datagrid("reload");
                        } else {
                            $.messager("提示",data.msg);
                        }
                    }

                    // onSubmit:function(param){
                    //     var values =  $("#role").combobox("getValues");
                    //     for(var i = 0; i < values.length; i++){
                    //         var rid  =  values[i];
                    //         param["roles["+i+"].rid"] = rid;
                    //     }
                    // },
                    // success:function (data) {
                    //     data = $.parseJSON(data);
                    //     if (data.success){
                    //         $.messager.alert("温馨提示",data.msg);
                    //         $("#dialog").dialog("close");
                    //         /* Reload data into datagrid */
                    //         $("#dg").datagrid("reload");
                    //     } else {
                    //         $.messager.alert("温馨提示",data.msg);
                    //     }
                    // }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }]
    });

});