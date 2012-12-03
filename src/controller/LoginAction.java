package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.User;
import formbeans.LoginFormBean;

public class LoginAction extends Action {
	private FormBeanFactory<LoginFormBean> formBeanFactory = FormBeanFactory
			.getInstance(LoginFormBean.class);
	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "login.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		HttpSession session = request.getSession();
		
		request.setAttribute("title", "Surprizer - Log In");
		
		//Is the user already logged in?
		if (session.getAttribute("user") != null) {
			return "yourLists.do";
		}
		LoginFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "index.jsp";
		}
		
		request.setAttribute("form", form);
		
		

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "index.jsp";
		}

		// Look up the user
		User user;
		try {
			user = userDAO.lookup(form.getEmail());
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("DAO errors");
			return "index.jsp";
		}
		
		// Check the password
		if (user.checkPassword(form.getPassword()) == false) {
			errors.add("Incorrect password");
			return "index.jsp";
		}

		// Attach (this copy of) the user bean to the session
		session.setAttribute("user", user);

		if(user.isEmailVerified())
			return request.getContextPath() + "/yourLists.do";
		else
			return request.getContextPath() + "/yourLists.do";
	}
}
