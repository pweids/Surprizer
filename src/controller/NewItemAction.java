package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.ItemDAO;
import model.Model;
import model.UserDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.GiftList;
import databeans.Item;
import databeans.User;
import formbeans.LoginFormBean;
import formbeans.NewItemFormBean;
import formbeans.NewListFormBean;

public class NewItemAction extends Action {
	private FormBeanFactory<NewItemFormBean> formBeanFactory = FormBeanFactory
			.getInstance(NewItemFormBean.class);
	private GiftListDAO giftlistDAO;
	private ItemDAO itemDAO;
	

	public NewItemAction(Model model) {
		giftlistDAO = model.getGiftListDAO();
		itemDAO = model.getItemDAO();
	}

	@Override
	public String getName() {
		return "newItem.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		GiftList list = (GiftList) session.getAttribute("list");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		NewItemFormBean form = null;

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
			return "page-newItem.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "page-newItem.jsp";
		}

		if(list == null)
			System.out.println("list is null");
		
		// Create the new user bean
		Item item = new Item(list.getListId(), form.getName(), form.getDescription(), form.getPrice(), form.getUrl());
		try {
			item = itemDAO.create(item);
			list.incNumItems();
			giftlistDAO.modify(list);
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("db errors");
			return "page-newItem.jsp";
		}
		System.out.println("new Item created");
		
		return request.getContextPath() + "/viewList.do";
	}
	

}
