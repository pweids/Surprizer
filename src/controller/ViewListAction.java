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
import formbeans.EditListFormBean;
import formbeans.LoginFormBean;

public class ViewListAction extends Action{
		private GiftListDAO giftListDAO;
		private ItemDAO itemDAO;
		private UsersListsDAO usersListsDAO;
		
		public ViewListAction(Model model) {
			giftListDAO = model.getGiftListDAO();
			itemDAO = model.getItemDAO();
			usersListsDAO = model.getUsersListsDAO();
		}

		@Override
		public String getName() {
			return "viewList.do";
		}

		@Override
		public String perform(HttpServletRequest request) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			GiftList giftList = (GiftList) session.getAttribute("list");
			
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);

			request.setAttribute("title", "Surprizer - List View");
			
			if(giftList == null){ 
				System.out.println("Session Giftlist was null, check param");
				String listIdString = request.getParameter("list");
				int listId;
				if(listIdString != null){
					listId = Integer.parseInt(listIdString);
					try {
						giftList = giftListDAO.lookup(listId);
					} catch (DAOException e) {
						errors.add(e.getMessage());
						//return "page-yourLists.jsp";
						return "viewList.jsp";
					}
				}
				else {
					errors.add("I don't know what list to show");
					//return "page-yourLists.jsp";
					return "viewList.jsp";
				}
			}
			
			Item items[] = null;
			try {
				items = itemDAO.getListItems(giftList);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				//return "page-yourLists.jsp";
				return "viewList.jsp";
			}
			request.setAttribute("items", items);
			
			try {
				session.setAttribute("following", usersListsDAO.isFollowing(user, giftList));
			} catch (DAOException e) {
				errors.add(e.getMessage());
				//return "page-yourLists.jsp";
				return "viewList.jsp";
			}
			
			session.setAttribute("user", user);
			session.setAttribute("list", giftList);
			
			
			
			//return "page-yourLists.jsp";
			return "viewList.jsp";
			
			//if(user.isVerified())
			//	return "page-yourLists.jsp";
			//else
			//	return request.getContextPath() + "/verifyEmail.do";

		}
	}

