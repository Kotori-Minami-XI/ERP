<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>企业用户管理系统</title>
    <link href="./static/css/base.css" rel="stylesheet">
    <link href="./static/css/login.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>
    <script>
        $(function () {
            $("#loginBtn").click(function () {
                var formData = $('#userForm').serialize();
                $.post(
                    "/login.action",
                    formData,
                    function (data) {
                        // Convert json string into json data
                        data = $.parseJSON(data);
                        if (data.result) {
                            window.location.href = "${pageContext.request.contextPath}/index.jsp";
                        } else {
                            alert(data.msg);
                        }
                    });
            });


        })
    </script>
</head>
<body class="white">
<div class="login-hd">
    <div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="hd-inner">
        <span class="logo"></span>
        <span class="split"></span>
        <span class="sys-name">企业用户管理系统</span>
    </div>
</div>
<div class="login-bd">
    <div class="bd-inner">
        <div class="inner-wrap">
            <div class="lg-zone">
                <div class="lg-box">
                    <div class="lg-label"><h4>用户登录</h4></div>

                    <form id="userForm">
                        <div class="lg-username input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="text" value="Kotori" name="username" placeholder="请用户名">
                        </div>
                        <div class="lg-password input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="password" value="1234" name="password"  placeholder="请输入密码">
                        </div>

                        <div class="enter">
                            <a href="javascript:;" class="purchaser" id="loginBtn">点击登录</a>
                        </div>

                    </form>
                    <div class="line line-y"></div>
                    <div class="line line-g"></div>
                </div>
            </div>
            <div class="lg-poster"></div>
        </div>
    </div>
</div>
<div class="login-ft">
    <div class="ft-inner">
        <div class="about-us">
            <a href="#">关于我们</a>
            <a href="#">Kotori学院</a>
            <a href="#">服务条款</a>
            <a href="#">联系方式</a>
        </div>
        <div class="address"> 内容版权均归Kotori 版权所有</div>
        <div class="other-info">建议使用IE8及以上版本浏览器&nbsp;沪ICP备&nbsp;XXXXXXXXX号&nbsp;E-mail：XXXXXXXX@qq.com</div>
    </div>
</div>


<script type="text/javascript">

</script>
</body>
</html>
