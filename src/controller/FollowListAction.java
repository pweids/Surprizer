package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.ItemDAO;
import model.Model;
import model.UsersListsDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.GiftList;
import databeans.Item;
import databeans.User;
import databeans.UsersLists;
import formbeans.EditListFormBean;
import formbeans.LoginFormBean;

public class FollowListAction extends Action{
	private UsersListsDAO usersListsDAO;
	private GiftListDAO giftListDAO;
	
	public FollowListAction(Model model) {
		usersListsDAO= model.getUsersListsDAO();
		giftListDAO = model.getGiftListDAO();
	}

	@Override
	public String getName() {
		return "followList.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		// Look up the user
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		
		String listIdString = request.getParameter("list");
		int listId;
		GiftList giftList = null;
		if(listIdString != null){
			listId = Integer.parseInt(listIdString);
			try {
				giftList = giftListDAO.lookup(listId);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				return "page-yourLists.jsp";
			}
		}
		else {
			errors.add("I don't know what list to follow");
			return "page-yourLists.jsp";
		}
		
		
		
		UsersLists userList = new UsersLists(user.getUserId(), giftList.getListId());
		try {
			userList = usersListsDAO.create(userList);
		} catch (DAOException e) {
			errors.add(e.getMessage());
			System.out.println("db errors");
			return "page-viewList.jsp";
		}
		session.setAttribute("list", giftList);
		return request.getContextPath() + "/viewList.do";
	}
}
