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
import formbeans.EditUserFormBean;

public class EditUserAction extends Action {
	private FormBeanFactory<EditUserFormBean> formBeanFactory = FormBeanFactory
			.getInstance(EditUserFormBean.class);
	private UserDAO userDAO;

	public EditUserAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "editUser.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EditUserFormBean form = null;
		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Edit Suprizer User");
		
		if (!form.isPresent()) {
			return "page-editUser.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "page-editUser.jsp";
		}

		//current user
		User currentUser = (User) session.getAttribute("user");
		
		// Look up the user
		User modifiedUser = new User();
		modifiedUser.setName(form.getName());
		modifiedUser.setEmail(currentUser.getEmail());
		modifiedUser.setPassword(form.getPassword());
		
		try {
			modifiedUser = userDAO.modify(modifiedUser);
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("DAO errors");
			return "page-editUser.jsp";
		}

		// Attach (this copy of) the user bean to the session
		session.setAttribute("user", modifiedUser);

		// CHANGE SUCCESSFUL
		return request.getContextPath() + "/yourLists.do";
	}
}
