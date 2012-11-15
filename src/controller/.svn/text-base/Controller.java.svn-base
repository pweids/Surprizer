package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;
import databeans.User;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private UserDAO userDAO;

	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}

		Model model = new Model(getServletConfig());
		userDAO = model.getUserDAO();

		//USER ACTIONS
		Action.add(new LoginAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new VerifyEmailAction(model));
		Action.add(new EditUserAction(model));
		Action.add(new LogoutAction(model));
		
		Action.add(new YourListsAction(model));
		Action.add(new NewListAction(model));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/*
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login).
	 * 
	 * @param request
	 * 
	 * @return the next page (the view)
	 */
	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		User user = (User) session.getAttribute("user");
		String action = getActionName(servletPath);

		//System.out.println("Controller: " + action);
		
		if (action.equals("login.do") || action.equals("register.do") || action.equals("verifyEmail.do")) {
			// Allow these actions without logging in
			return Action.perform(action, request);
		}
		
		if (user == null) {
			System.out.println("Controller: user == null");
			// If the user hasn't logged in, direct him to the login page
			return Action.perform("login.do", request);
		}
		
		//if(!user.isVerified()){
		//	System.out.println("Controller: user != verified");
		//	// If the user hasn't verified their email, direct him to that page
		//	return Action.perform("yourLists.do", request);
		//}
		
		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	/*
	 * If nextPage is null, send back 404 If nextPage starts with a '/',
	 * redirect to this page. In this case, the page must be the whole servlet
	 * path including the webapp name Otherwise dispatch to the page (the view)
	 * This is the common case
	 */
	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// System.out.println("nextPage="+nextPage);

		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		if (nextPage.charAt(0) == '/') {
			String host = request.getServerName();
			String port = ":" + String.valueOf(request.getServerPort());
			if (port.equals(":80"))
				port = "";
			response.sendRedirect("http://" + host + port + nextPage);
			return;
		}

		RequestDispatcher d = request.getRequestDispatcher("/" + nextPage);
		d.forward(request, response);
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
