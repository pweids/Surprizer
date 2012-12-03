package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.Model;
import model.UserDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.GiftList;
import databeans.User;
import formbeans.LoginFormBean;
import formbeans.NewListFormBean;

public class NewListAction extends Action {
	private FormBeanFactory<NewListFormBean> formBeanFactory = FormBeanFactory
			.getInstance(NewListFormBean.class);
	private GiftListDAO giftlistDAO;

	public NewListAction(Model model) {
		giftlistDAO = model.getGiftListDAO();
	}

	@Override
	public String getName() {
		return "newList.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		NewListFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Surprizer - Create New List");
		
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "mylists.jsp";
		}

		

		// Create the new user bean
		GiftList list = new GiftList(form.getListName(), form.getDescription(), user.getUserId(), user.getName(), form.getPrivacySetting());
		try {
			list = giftlistDAO.create(list);
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("db errors");
			return "mylists.jsp";
		}
		System.out.println("new List created");
				
		return request.getContextPath() + "/yourLists.do";
		
	}
	

}
