<%--
  该页面用于消息推送
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>测试requestbody</title>
</head>
<script type="text/javascript" src="<%=path%>/resource/static/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/resource/static/js/sockjs.min.js"></script>
<body>
当前登录用户：${pageContext.session.getAttribute("people").userName}<br>
<input type="text" id="text">
<button id="btn1" value="发送给所有人">发送给所有人</button>
<button id="btn2" value="发送给特定用户">发送给特定用户</button>
<div id="msg"></div>

</body>
</html>
<script>
    console.log("path:"+"<%=path%>");
    $(function() {
        var websocket;
        // 首先判断是否 支持 WebSocket
        if('WebSocket' in window) {
            websocket = new WebSocket("ws://192.168.0.168:8080/ssmPureBackend/websocket");
        } else if('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://192.168.0.168:8080/ssmPureBackend/websocket");
        } else {
            websocket = new SockJS("http://192.168.0.168:8080/ssmPureBackend/sockjs/websocket");
        }
        // 打开时
        websocket.onopen = function(evnt) {
            console.log("  websocket.onopen  ");
        };
        // 处理消息时
        websocket.onmessage = function(evnt) {
            alert("你有一条新消息");
            $("#msg").html(evnt.data);
        };
        websocket.onerror = function(evnt) {
            console.log("  websocket.onerror  ");
        };
        websocket.onclose = function(evnt) {
            console.log("  websocket.onclose  ");
        };
        // 点击了发送消息按钮的响应事件
        $("#btn2").bind("click",function () {
            var url = "${pageContext.request.contextPath}/websocket/sendMsg";
            var content =  $("#text").val();
            var toUserName = "admin"
            $.ajax({
                data: "content=" + content + "&toUserName=" + toUserName,
                type: "get",
                dataType: 'text',
                async: false,
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                encoding: "UTF-8",
                url: url,
                success: function (data) {
                    alert(data.toString());
                },
                error: function (msg) {
                    alert(msg);
                },
            });
        })
        $("#btn1").bind("click",function () {
            var url = "${pageContext.request.contextPath}/websocket/sendMsg2";
            var content =  $("#text").val();
            $.ajax({
                data: "content=" + content ,
                type: "get",
                dataType: 'text',
                async: false,
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                encoding: "UTF-8",
                url: url,
                success: function (data) {
                    alert(data.toString());
                },
                error: function (msg) {
                    alert(msg);
                },
            });
        })
    });
</script>