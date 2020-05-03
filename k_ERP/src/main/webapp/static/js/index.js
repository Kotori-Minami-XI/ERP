$(function () {
    $("#tabs").tabs({
        fit:true
    });

    $('#tree').tree({
        url:'/getMenuTree.action',
        lines:true,
        onSelect: function(node){
            var exists =   $("#tabs").tabs("exists",node.text);
            if(exists){
                $("#tabs").tabs("select",node.text);
            }else {
                if (node.url !=''&& node.url !=null){
                    $("#tabs").tabs("add",{
                        title:node.text,
                        content:"<iframe src="+node.url +" frameborder='0' width='100%' height='100%'></iframe>",
                        closable:true
                    })
                }
            }
        },
        // Load first item when the page finishes loading
        onLoadSuccess: function (node, data) {
            console.log(data[0].children[0].id);
            if (data.length > 0) {
                var n = $('#tree').tree('find', data[0].children[0].id);
                $('#tree').tree('select', n.target);
            }
        }
    });
});