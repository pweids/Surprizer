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

public class MarkItemAction extends Action {
	private GiftListDAO giftlistDAO;
	private ItemDAO itemDAO;
	

	public MarkItemAction(Model model) {
		giftlistDAO = model.getGiftListDAO();
		itemDAO = model.getItemDAO();
	}

	@Override
	public String getName() {
		return "markItem.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		GiftList list = (GiftList) session.getAttribute("list");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		request.setAttribute("title", "Surprizer - Edit Item");
		
	    String itemStr = request.getParameter("item");
	    System.out.println("itemStr is null. This is a problem");
		if(itemStr != null){
			int itemId = Integer.valueOf(itemStr);
			Item item = null;
			try {
				System.out.println("Looking up item");
				item = itemDAO.lookup(itemId);
				item.setUserId(user.getUserId());
				item = itemDAO.modify(item);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				System.out.println("db errors" + e.getMessage());
				return request.getContextPath() + "/viewList.do";
			}
		}
	
		return request.getContextPath() + "/viewList.do";
	}
	

}
