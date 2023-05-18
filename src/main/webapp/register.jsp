<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>云R记</title>
  <link href="resources/css/register.css" rel="stylesheet" type="text/css" />
  <script src="resources/js/jquery-1.11.3.js" type=text/javascript></script>
  <script src="resources/js/util.js" type=text/javascript></script>
  <script src="resources/js/config.js" type=text/javascript></script>
<%--  <script src="resources/js/register.js" type="text/javascript" defer="true"></script>--%>
</head>
<body>
<!--head-->
<div id="head">
  <div class="top">
    <div class="fl yahei18">开启移动办公新时代！</div>
  </div>
</div>

<!--register box-->
<div class="wrapper">
  <div id="box">
    <div class="registerbar">用户注册</div>
    <div id="tabcon">
      <div class="box show">
        <form action="user" method="post" id="registerForm">
          <input type="hidden" name="actionName" value="register"/>
          <input type="text" class="user yahei16" id="username" value="${resultInfo.result.uname}" name="username"/><br /><br />
          <input type="password" class="pwd yahei16" id="userpwd" value="" name="password"/><br /><br />
          <input type="password" class="pwd yahei16" id="usercpwd" value="" name="cpassword"/><br /><br />
<%--          <input name="rem" type="checkbox" value="1"  class="inputcheckbox"/> <label>记住我</label>&nbsp; &nbsp;--%>
          <span id="msg" style="display:inline-block; text-align: center; color: red;font-size: 12px">${resultInfo.msg}</span><br />
          <input type="button" class="log jc yahei16" value="注 册" onclick="checkRegister()" /> &nbsp; &nbsp; &nbsp;
          <input type="button" onclick="location.href=('login.jsp')" value="登 录" class="reg jc yahei18" />
        </form>
      </div>
    </div>
  </div>
</div>

<div id="flash">
  <div class="pos">
    <a bgUrl="resources/images/banner-bg1.jpg" id="flash1" style="display:block;"><img src="resources/images/banner_pic1.png"></a>
    <a bgUrl="resources/images/banner-bg2.jpg" id="flash2"                       ><img src="resources/images/banner-pic2.jpg"></a>
  </div>
  <div class="flash_bar">
    <div class="dq" id="f1" onclick="changeflash(1)"></div>
    <div class="no" id="f2" onclick="changeflash(2)"></div>
  </div>
</div>

<!--bottom-->
<div id="bottom">
  <div id="copyright">
    <div class="quick">
      <ul>
        <li><input type="button" class="quickbd iphone" onclick="location.href='http://lezijie.com'" /></li>
        <li><input type="button" class="quickbd android" onclick="location.href='http://lezijie.com'" /></li>
        <li><input type="button" class="quickbd pc" onclick="location.href='http://lezijie.com'" /></li>
        <div class="clr"></div>
      </ul>
      <div class="clr"></div>
    </div>
    <div class="text">
      Copyright © 2006-2026  <a href="http://www.shsxt.com">上海乐字节</a>  All Rights Reserved
    </div>
  </div>
</div>
</body>
<script>
  
  $("#username").change(function (){
    var username = $("#username").val();
    $.ajax({
      method:"get",
      url:"http://localhost:8082/note/user",
      data:{"username":username, "actionName":"ajaxRegister"},
      success:function (data){
        if (data != ""){
          $("#username").css({"border":"1px solid red"});
        }
      }
    })
  })
</script>
</html>