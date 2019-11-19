<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>

<%
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from article");
%>

<html>
<head>
    <title>Title</title>
</head>
<body>

<table border="1">
<%
    while (rs.next()) {
%>
    <tr>
        <td><%=rs.getInt("id")%></td>
        <td><%=rs.getString("cont")%></td>
    </tr>
<%
    }
%>
</table>

</body>
</html>

<%
    rs.close();
    stmt.close();
    conn.close();
%>
