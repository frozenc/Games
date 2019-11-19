import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BBS
 * 2019/11/10 19:53
 * test Session
 *
 * @author Chan
 * @since
 **/
public class ShowSession extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Session Tracking Example";
        HttpSession session = request.getSession(true);
        String heading;
        Integer accessCount = (Integer) session.getAttribute("accessCount");
        if (accessCount == null) {
            accessCount = new Integer(0);
            heading = "Welcome, new comer";
        } else {
            heading = "Welcome Back";
            accessCount = new Integer(accessCount.intValue() + 1);
        }

        session.setAttribute("accessCount", accessCount);

        out.println("<html><head><title>Session 追踪</title></head>");
        out.println("<body><table border=\"1\"><tr>");
        out.println("<td>creation time</td><td>" + session.getCreationTime() + "</td></tr>");
        out.println("<tr><td>last access time</td><td>" + session.getLastAccessedTime() + "</td></tr>");
        out.println("<tr><td>number of previous account</td><td>" + session.getAttribute("accessCount") + "</td></tr></table></body></html>");
    }
}
