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
import formbeans.RegisterFormBean;

public class RegisterAction extends Action {
	private FormBeanFactory<RegisterFormBean> formBeanFactory = FormBeanFactory
			.getInstance(RegisterFormBean.class);
	private UserDAO userDAO;

	public String getName() {
		return "register.do";
	}

	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		request.setAttribute("title", "Surprizer - Registration");

		
		RegisterFormBean form = null;
		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		
				
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
	    if (!form.isPresent()) {
			System.out.println("new form");
	        return "register.jsp";
	    }
	    
	    request.setAttribute("form",form);

		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("validation errors");
			return "register.jsp";
		}

		
		// Create the new user bean
		User user = new User(form.getName(), form.getEmail(), form.getPassword());
		try {
			user = userDAO.create(user);
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("db errors");
			return "register.jsp";
		}
		System.out.println("new user " + user.getName() + " created");
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		return request.getContextPath() + "/yourLists.do";		
	}

}
