import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

/**
 * BBS
 * 2019/11/7 18:43
 *
 * @author Chan
 * @since
 **/
public class HelloWorldServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doGet");
        System.out.println("hello!");
        response.setContentType("text/html;charset=gb2312");

        /*获取所有参数，类似于iterater
        * Enumeration paramNames = request.getParameterNames();
        * request.getParameterMap();
        * */

        try {
            response.getWriter().write("<a href='http://baidu.com'>go</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
