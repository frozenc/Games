<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    request.setCharacterEncoding("utf-8");

    int id = Integer.parseInt(request.getParameter("id"));
    int rootId = Integer.parseInt(request.getParameter("rootId"));
    String title = request.getParameter("title");
    String cont = request.getParameter("cont");
    title = title.trim();
    cont = cont.trim();

    if (title == null) {
        out.println("error! please use my bbs in the right way!");
        return;
    }

    if (cont == null) {
        out.println("error! please use my bbs in the right way!");
        return;
    }

    if (title.equals("")) {
        out.println("title could not be empty");
        return;
    }

    if (cont.equals("")) {
        out.println("cont could not be empty");
        return;
    }

    cont = cont.replaceAll("\n", "<br>");

    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    conn.setAutoCommit(false);

    String sql = "insert into article values (null, ?, ?, ?, ?, now(), 0)";
    PreparedStatement preStmt = conn.prepareStatement(sql);
    Statement stmt = conn.createStatement();

    preStmt.setInt(1, id);
    preStmt.setInt(2, rootId);
    preStmt.setString(3, title);
    preStmt.setString(4, cont);
    preStmt.executeUpdate();

    stmt.executeUpdate("update article set isleaf = 1 where id =" + id);

    conn.commit();
    conn.setAutoCommit(true);

    stmt.close();
    preStmt.close();
    conn.close();

    response.sendRedirect("index.jsp");

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p color="red" size="7">
    OK!
</p>
</body>
</html>
