<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/11/3
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String id = request.getParameter("id");
    String rootId = request.getParameter("rootId");
%>

<html>
<head>
    <title>Title</title>
    <script>
        <!--
        //javascript去空格函数
        function LTrim(str){ //去掉字符串 的头空格
            var i;
            for(i=0;i<str.length; i++) {
                if(str.charAt(i)!=" ") break;
            }
            str = str.substring(i,str.length);
            return str;
        }

        function RTrim(str){
            var i;
            for(i=str.length-1;i>=0;i--){
                if(str.charAt(i)!=" "&&str.charAt(i)!=" ") break;
            }
            str = str.substring(0,i+1);
            return str;
        }
        function Trim(str){

            return LTrim(RTrim(str));

        }

        function check() {
            if(Trim(document.reply.title.value) == "") {
                alert("please intput the title!");
                document.reply.title.focus();
                return false;
            }

            if(Trim(document.reply.cont.value) == "") {
                alert("plsease input the content!");
                document.reply.cont.focus();
                return false;
            }

            return true;

        }
        -->
    </script>
</head>
<body>
<form action="ReplyOK.jsp" name=reply method="post" onsubmit="return check()">
    <input type="hidden" name="id" value="<%= id%>">
    <input type="hidden" name="rootId" value="<%= rootId%>">
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
