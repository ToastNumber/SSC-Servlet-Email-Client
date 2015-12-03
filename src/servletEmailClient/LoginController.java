package servletEmailClient;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Model m = new Model();
		boolean loginSuccessful = m.setupSession(username, password);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if (loginSuccessful) {
			// Get the session
			HttpSession session = request.getSession(true);
			// Set the timeout to 5 minutes
			session.setMaxInactiveInterval(5 * 60);
			
			// Then set the user name and password for the session
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("message-writer.jsp");
			dispatcher.include(request, response);
		} else {
			out.println("User name or password incorrect. Please try again.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.include(request, response);
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
