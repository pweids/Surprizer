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
import formbeans.EditItemFormBean;
import formbeans.LoginFormBean;
import formbeans.NewItemFormBean;
import formbeans.NewListFormBean;

public class EditItemAction extends Action {
	private FormBeanFactory<EditItemFormBean> formBeanFactory = FormBeanFactory
			.getInstance(EditItemFormBean.class);
	private GiftListDAO giftlistDAO;
	private ItemDAO itemDAO;
	

	public EditItemAction(Model model) {
		giftlistDAO = model.getGiftListDAO();
		itemDAO = model.getItemDAO();
	}

	@Override
	public String getName() {
		return "editItem.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		GiftList list = (GiftList) session.getAttribute("list");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		EditItemFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Surprizer - Edit Item");
		
		String itemStr = request.getParameter("item");
		if(itemStr != null){ 
			Item item = null;
			try {
				int itemId = Integer.valueOf(itemStr);
				item = itemDAO.lookup(itemId);
			
				request.setAttribute("item", item);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				return "page-editItem.jsp";
			}
		}
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "page-editItem.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "page-editItem.jsp";
		}
		

		if(list == null)
			System.out.println("list is null. This is a problem");
		
	    itemStr = request.getParameter("itemId");
	    System.out.println("itemStr is null. This is a problem");
		if(itemStr != null){
			int itemId = Integer.valueOf(itemStr);
			Item item = null;
			try {
				System.out.println("Looking up item");
				item = itemDAO.lookup(itemId);
				item.setListId(list.getListId());
				item.setName(form.getName());
				item.setDescription(form.getDescription());
				item.setPrice(form.getPrice());
				item.setUrl(form.getUrl());
				item = itemDAO.modify(item);
				System.out.println("modifying item: " + list.getListId() + form.getName() + form.getDescription() + form.getPrice() + form.getUrl() );
			} catch (DAOException e) {
				errors.add(e.getMessage());
				System.out.println("db errors" + e.getMessage());
				return request.getContextPath() + "/viewList.do";
			}
		}
	
		return request.getContextPath() + "/viewList.do";
	}
	

}
