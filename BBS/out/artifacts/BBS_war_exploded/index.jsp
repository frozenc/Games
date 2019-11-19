<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>

<%
  String admin = (String)session.getAttribute("admin");

  if (admin != null && admin.equals("admin")) {
    login = true;
  }
%>

<%!
  String str = "";
  boolean login = false;
  private void tree(Connection conn, int id, int level) {
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.createStatement();
      String sql = "select * from article where pid = " + id;
      rs = stmt.executeQuery(sql);
      String strLogin = "";
      String preStr = "";
      for (int i = 0; i < level; i++) {
        preStr += "----";
      }
      while (rs.next()) {
        if (login) {
          strLogin = "<td><a href='Delete.jsp?id=" + rs.getInt("id") + "&pid=" + rs.getInt("pid") + "'>删除</a></td>";
        }
        str += "<tr><td>" + rs.getInt("id") + "</td><td>"
                + preStr + "<a href='ShowArticleDetail.jsp?id=" + rs.getInt("id") + "'>" + rs.getString("title") + "</a></td>" +
                strLogin +
                "</tr>";
        if(rs.getInt("isleaf") != 0) {
          tree(conn, rs.getInt("id"), level+1);
        }
      }
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
  Class.forName("com.mysql.cj.jdbc.Driver");
  String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
  Connection conn = DriverManager.getConnection(url);
  Statement stmt = conn.createStatement();
  ResultSet rs = stmt.executeQuery("select * from article where pid = 0");
  String strLogin = "";
  while(rs.next()) {
    if (login) {
      strLogin = "<td><a href='Delete.jsp?id=" + rs.getInt("id") + "&pid=" + rs.getInt("pid") + "'>删除</a></td>";
    }

    str += "<tr><td>" + rs.getInt("id") + "</td><td>"
            + "<a href='ShowArticleDetail.jsp?id=" + rs.getInt("id") + "'>" + rs.getString("title") + "</a></td>" +
            strLogin +
            "</tr>";
    if(rs.getInt("isleaf") != 0) {
      tree(conn, rs.getInt("id"), 1);
    }
  }
  rs.close();
  stmt.close();
  conn.close();
%>

<html>
<head>
  <title>Title</title>
</head>
<body>
<a href="Post.jsp">发表新帖</a>
<a href="Login.jsp">登录</a>
<table border="1">
<%= str %>
</table>

</body>
</html>

<%
  System.out.println("str" + str);
  login = false;
  str = "";
%>