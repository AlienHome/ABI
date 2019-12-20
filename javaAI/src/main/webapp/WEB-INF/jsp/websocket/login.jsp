<%--
  该页面用于登录，将用户存于session
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head>
    <title>测试websocket</title>
    <base href="<%=basePath%>">
  </head>
  <body>
  <form:form action="websocket/login">
    <input type="text" name="userName" value="xwj"/>
    <input type="text" name="passWord" value="123123"/>
    <input type="submit" value="测试websocket"/>
  </form:form>
  </body>
</html>