<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String action = request.getParameter("action");

    if(action != null && action.equals("post")) {
        String title = request.getParameter("title");
        String cont = request.getParameter("cont");

        System.out.println(title);
        System.out.println(cont);

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
        Connection conn = DriverManager.getConnection(url);

        conn.setAutoCommit(false);
        String sql = "insert into article values (null, 0, ?, ?, ?, now(), 0)";
        PreparedStatement preStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Statement stmt = conn.createStatement();

        preStmt.setInt(1, -1);
        preStmt.setString(2, title);
        preStmt.setString(3, cont);
        preStmt.executeUpdate();

        ResultSet rsKey = preStmt.getGeneratedKeys();
        rsKey.next();
        int key = rsKey.getInt(1);
        rsKey.close();

        stmt.executeUpdate("update article set rootid = " + key + " where id = " + key);

        conn.commit();
        conn.setAutoCommit(true);
        stmt.close();
        preStmt.close();
        conn.close();

        response.sendRedirect("ShowArticleFlat.jsp");
    }
%>

<html>
<head>
    <title>Post</title>
</head>
<body>
<form action="Post.jsp" method="post">
    <input type="hidden" name="action" value="post">
    <table border="1">
        <tr>
            <td>
                <input type="text" name="title" size="80">
            </td>
        </tr>
        <tr>
            <td>
                <textarea cols="80" rows="12" name="cont"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
