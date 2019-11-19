<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String id = request.getParameter("id");

    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from article where id =" + id);
%>


<html>
<head>
    <title>ShowArticleDetail</title>
</head>
<body>
<%
    if (rs.next()) {
%>
<table border="1">
    <tr>
        <td>ID</td>
        <td><%= rs.getInt("id")%></td>
    </tr>
    <tr>
        <td>Title</td>
        <td><%= rs.getString("title")%></td>
    </tr>
    <tr>
        <td>Content</td>
        <td><%= rs.getString("cont")%></td>
    </tr>
</table>
<a href="Reply.jsp?id=<%= rs.getInt("id")%>&rootId=<%= rs.getInt("rootid")%>">回复</a>
<%
    }
    rs.close();
    stmt.close();
    conn.close();
%>
</body>
</html>
