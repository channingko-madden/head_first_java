/** @brief A very simple servlet pg. 627
 *  Note: Will not compile without javax.servlet package (available in Java EE or need to download some other way */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyServletA extends HttpServlet
{
	/** @brief Handle HTTP GET messages
	 *  @param[in] request Client's request
	 *  @response Use to send back a response (http page) */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html"); // tells server & browsers that what's coming back is html

		PrintWriter out = response.getWriter(); // get output stream to write response with

		String message = "If you're reading this, PogChamp!";

		out.println("<HTML><BODY>");
		out.println("<H1>" + message + "</H1>");
		out.println("</BODY></HTML>);
		out.close();
	}
}

