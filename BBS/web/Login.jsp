<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/4
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String action = request.getParameter("action");
    if (action != null && action.equals("login")) {
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(username == null && !username.equals("admin"));

        if (username == null || !username.equals("admin")) {
            out.println("username not correct!");
        } else if (password == null || !password.equals("admin")) {
            out.println("password not correct!");
        } else {
            out.println("welcome admin!");
            session.setAttribute("admin", "admin");
            response.sendRedirect("index.jsp");
        }
    }

%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="Login.jsp" name="login">
        <input type="hidden" name="action" value="login">
        <table border="1">
            <tr>
                <td>
                    <input type="text" name="username" size="30">
                </td>
            </tr>
            <tr>
                <td>
                    <input cols="30" type="password" name="password">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="登录">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
