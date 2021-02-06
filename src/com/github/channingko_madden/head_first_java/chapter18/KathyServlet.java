/** @brief Servlet that uses the PhraseOMatic pg 629
 *  Requires javax.servlet */

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class KathyServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String title = "PhraseOMatic has generated the following phrase.";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<HTML><HEAD><TITLE>");
		out.println("PhraseOmatic");
		out.println("</TITLE></HEAD><BODY>");
		out.println("<H1>" + title + <"</H1>");
		out.println("<P>" + PhraseOMatic.makePhrase());
		out.println("<P><a href=\"KathyServlet\">make another phrase</a></p>");
		out.println("</BODY></HTML>");

		out.close();
	}
}
