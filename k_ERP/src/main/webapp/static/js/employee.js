$(function () {
    $('#datagrid').datagrid({
        url:"/getEmployeeList.action",
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'inputtime',title:'入职时间',width:100,align:'center'},
            {field:'tel',title:'电话',width:100,align:'center'},
            {field:'email',title:'邮箱',width:100,align:'center'},
            {field:'department',title:'部门',width:100,align:'center'},
            {field:'state',title:'状态',width:100,align:'center'},
            {field:'admin',title:'管理员',width:100,align:'center'}
        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
    });
});