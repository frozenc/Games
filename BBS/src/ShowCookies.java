import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BBS
 * 2019/11/10 15:53
 *
 * @author Chan
 * @since
 **/
public class ShowCookies extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String title = "active Cookies";
        out.println("<html><head><title>获取客户端</title></head>"
                + "<body><h1>" + title + "</h1>"
                + "<table border=\"1\">"
                + "<tr><th>CookieName</th><th>CookieValue</th><tr>"
        );
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie cookie;
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                out.println("<tr>" +
                        "<td>" + cookie.getName()  + "</td>" +
                        "<td>" + cookie.getValue() + "</td><tr>"
                        );
            }
        }
        out.println("</table></body></html>");
    }
}
