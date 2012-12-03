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

import databeans.GiftList;
import databeans.Item;
import databeans.User;

public class DeleteListAction extends Action{
		private GiftListDAO giftListDAO;
		private ItemDAO itemDAO;
		private UsersListsDAO usersListsDAO;
		
		public DeleteListAction(Model model) {
			giftListDAO = model.getGiftListDAO();
			itemDAO = model.getItemDAO();
			usersListsDAO= model.getUsersListsDAO();
		}

		@Override
		public String getName() {
			return "deleteList.do";
		}

		@Override
		public String perform(HttpServletRequest request) {
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			String list = request.getParameter("list");
			Item items[]  = null;
			if(list != null){
				int listId = Integer.valueOf(list);
				try {
					GiftList giftList = giftListDAO.lookup(listId);
					if(giftList.getUserId() == user.getUserId()){
						giftListDAO.delete(listId);
						usersListsDAO.deleteList(listId);
						items = itemDAO.getListItems(giftList);
						for(Item i : items){
							itemDAO.delete(i.getItemId());
						}
					}
				} catch (DAOException e) {
					errors.add(e.getMessage());
					return request.getContextPath() + "/yourLists.do";
				}
			}
			return request.getContextPath() + "/yourLists.do";
			
			//if(user.isVerified())
			//	return "page-yourLists.jsp";
			//else
			//	return request.getContextPath() + "/verifyEmail.do";

		}
	}

