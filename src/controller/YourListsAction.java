package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.Model;
import model.UsersListsDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.GiftList;
import databeans.User;
import databeans.UsersLists;
import formbeans.LoginFormBean;

public class YourListsAction extends Action{
	private GiftListDAO giftListDAO;
	private UsersListsDAO usersListsDAO;
	
	public YourListsAction(Model model) {
		giftListDAO = model.getGiftListDAO();
		usersListsDAO = model.getUsersListsDAO();
	}

	@Override
	public String getName() {
		return "yourLists.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		session.removeAttribute("list");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		request.setAttribute("title", "Surprizer - Your Lists");

		GiftList[] userLists = null;
		try {
			userLists = giftListDAO.getUserLists(user);
		} catch (DAOException e) {
			errors.add(e.getMessage());
		}
		request.setAttribute("UserLists", userLists);
		
		UsersLists[] usersLists = null;
		try {
			usersLists = usersListsDAO.getUserLists(user);
		} catch (DAOException e) {
			errors.add(e.getMessage());
		}
		
		ArrayList<GiftList> sharedLists = new ArrayList<GiftList>(); 
		for(UsersLists ul : usersLists){
			try {
				sharedLists.add(giftListDAO.lookup(ul.getListId()));
			} catch (DAOException e) {
				errors.add(e.getMessage());
			}
		}
		request.setAttribute("SharedLists", sharedLists);
		
		return "mylists.jsp";
		
		//if(user.isVerified())
		//	return "page-yourLists.jsp";
		//else
		//	return request.getContextPath() + "/verifyEmail.do";

	}
}

