<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/5
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>

<%
    int pageSize = 3;

    String strPageNo = request.getParameter("pageNo");
    int pageNo;
    if (strPageNo == null || strPageNo.equals("")) {
        pageNo = 1;
    } else {
        try {
            pageNo = Integer.parseInt(strPageNo);
        } catch (NumberFormatException e) {
            pageNo = 1;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
    }


    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    Statement stmtCount = conn.createStatement();
    ResultSet rsCount = stmtCount.executeQuery("select count(*) from article where pid = 0");
    rsCount.next();
    int totalRecords = rsCount.getInt(1);

    int totalPages = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;
    if (pageNo > totalPages) pageNo = totalPages;
    int startPos = (pageNo - 1) * pageSize;


    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from article where pid = 0 order by pdate desc limit " + startPos + "," + pageSize );
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="Post.jsp">发表新帖</a>
<a href="Login.jsp">登录</a>
<table border="1">

<%
    while(rs.next()) {
%>
    <tr>
        <td>
            <%=rs.getString("cont")%>
        </td>
    </tr>
<%
    }
%>
</table>

共<%=totalPages%>页
<a href="ShowArticleFlat.jsp?pageNo=<%=pageNo-1%>"><</a>
第<%=pageNo %>页
<a href="ShowArticleFlat.jsp?pageNo=<%=pageNo+1%>">></a>

<form name="form1" action="ShowArticleFlat.jsp?pageNo=">
    <select name="pageNo" onchange="document.form1.submit()">
        <%
            for (int i = 1; i < totalPages + 1; i++) {
        %>
            <option value=<%=i%> <%=(pageNo == i)?"selected":""%>> 第<%=i%> 页
        <%
            }
        %>
    </select>
</form>

<form name="form2" action="ShowArticleFlat.jsp">
    <input type="text" size="4" name="pageNo" value="<%=pageNo%>"/>
    <input type="submit" value="go">
</form>

</body>
</html>

<%
    rs.close();
    stmt.close();
    conn.close();
%>