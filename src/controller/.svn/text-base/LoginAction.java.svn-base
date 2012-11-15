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

		LoginFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Suprizer Login");
		
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "page-login.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "page-login.jsp";
		}

		// Look up the user
		User user;
		try {
			user = userDAO.lookup(form.getEmail());
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("DAO errors");
			return "page-login.jsp";
		}
		
		// Check the password
		if (user.checkPassword(form.getPassword()) == false) {
			errors.add("Incorrect password");
			return "page-login.jsp";
		}

		// Attach (this copy of) the user bean to the session
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		if(user.isVerified())
			return request.getContextPath() + "/yourLists.do";
		else
			return request.getContextPath() + "/yourLists.do";
	}
}
