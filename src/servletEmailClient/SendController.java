package servletEmailClient;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet controller which handles a user login attempt.
 * @author Kelsey McKenna
 */
@WebServlet("/SendController")
public class SendController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the current session
		HttpSession session = request.getSession(true);

		// Set the content type for printing error messages etc.
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// If the user's credentials are no longer stored
		if (session.isNew()) {
			out.println("Session timeout.");
			// Go back to the home page
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.include(request, response);
		} else {
			// Since the session isn't new, we can get username and password
			final String username = (String) session.getAttribute("username");
			final String password = (String) session.getAttribute("password");

			// Get the email details
			String to = request.getParameter("to");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");

			Model m = new Model();
			boolean loginSuccessful = m.setupSession(username, password);

			if (loginSuccessful) {
				try {
					// Set up the message to send
					MimeMessage message = m.constructMimeMessage(username, to, subject, content);
					// Then send it
					m.sendEmail(message, username, password);

					// Show a notification that the email was send correctly.
					RequestDispatcher rd = request.getRequestDispatcher("/sending-success.jsp");
					rd.include(request, response);
				} catch (Exception e) {
					//Print an error message and stay on the current page
					out.println("Error sending message. Please try again.");
					RequestDispatcher rd = request.getRequestDispatcher("/message-writer.jsp");
					rd.include(request, response);
				}
			} else {
				// This block shouldn't occur unless something very strange happened, 
				// like username and password changed after logging in.
				
				out.println("Login credentials no longer valid.");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				rd.include(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
