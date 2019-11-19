import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BBS
 * 2019/11/10 17:38
 * testSession
 *
 * @author Chan
 * @since
 **/
public class SessionInfoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession mySession = request.getSession(true);

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>session information</title></head>");
        out.println("<body>");
        out.println("new session:" + mySession.isNew());
        out.println("<br>sessionId:" + mySession.getId());
        out.println("<br>session create time:" + new java.util.Date(mySession.getCreationTime()));
        out.println("<br>session last accessed time" + new java.util.Date(mySession.getLastAccessedTime()));
        out.println("<br>request information");
        out.println("<br>sessionId from request:" + request.getRequestedSessionId());
        out.println("<br>session id via cookies:" + request.isRequestedSessionIdFromCookie());
        out.println("<br>session id via url" + request.isRequestedSessionIdFromURL());
        out.println("<br>valid session id" + request.isRequestedSessionIdValid());
        out.println("<br><a href=" + response.encodeURL("SessionInfoServlet"));

        out.println("</body></html>");
        out.close();
    }
}
