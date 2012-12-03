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

public class UnfollowListAction extends Action{
	private GiftListDAO giftListDAO;
	private ItemDAO itemDAO;
	private UsersListsDAO usersListsDAO;
	
	public UnfollowListAction(Model model) {


		usersListsDAO= model.getUsersListsDAO();
		giftListDAO = model.getGiftListDAO();
		itemDAO = model.getItemDAO();
	}

	@Override
	public String getName() {
		return "unfollowList.do";
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
				usersListsDAO.delete(user.getUserId(), listId);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				System.out.println("db errors");
				return "viewList.jsp";
			}
		}
		else {
			errors.add("I don't know what list to unfollow");
			return "mylists.jsp";
		}
		
		
		
		session.setAttribute("list", giftList);
		return request.getContextPath() + "/yourLists.do";
	}
}
