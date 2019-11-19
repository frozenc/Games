import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * test
 * 2019/11/10 15:30
 *
 * @author Chan
 * @since
 **/
public class SetCookies extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (int i = 0; i < 3; i++) {
            Cookie cookie = new Cookie("Session-Cookie-" + i, "Cookie-Value-S" + i);
            response.addCookie(cookie);
            cookie = new Cookie("Persistent-Cookie-" + i,"Cookies-Value-S" + i);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }

        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String title = "Setting cookies";
        out.println("<html><head><title>设置cookie</title></head>"
                        + "<body>"
                        + "<a href=\"ShowCookies\">ShowCookies</a>"
                        + "</body></html>"
                );
    }
}
