<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String admin = (String)session.getAttribute("admin");
    if (admin == null || !admin.equals("admin")) {
        out.println("小贼，别想过我这关！");
        return;
    }
%>

<%!
    private void del(Connection conn, int id) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String sql = "select * from article where pid = " + id;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                del(conn, rs.getInt("id"));
            }
            stmt.executeUpdate("delete from article where id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    int pid = Integer.parseInt(request.getParameter("pid"));

    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);
    conn.setAutoCommit(false);

    del(conn, id);
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select count(*) from article where pid = " + pid);
    rs.next();
    int count = rs.getInt(1);
    rs.close();
    stmt.close();

    if(count <= 0) {
        Statement stmtUpdate = conn.createStatement();
        stmtUpdate.executeUpdate("update article set isleaf = 0 where id ="  + pid);
    }

    conn.commit();
    conn.setAutoCommit(true);
    conn.close();

    response.sendRedirect("index.jsp");
%>

<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
