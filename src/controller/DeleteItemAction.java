package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.ItemDAO;
import model.Model;

import org.mybeans.dao.DAOException;

import databeans.GiftList;
import databeans.Item;
import databeans.User;

public class DeleteItemAction extends Action{
		private ItemDAO itemDAO;
		private GiftListDAO giftlistDAO;
		
		public DeleteItemAction(Model model) {
			giftlistDAO = model.getGiftListDAO();
			itemDAO = model.getItemDAO();
		}

		@Override
		public String getName() {
			return "deleteItem.do";
		}

		@Override
		public String perform(HttpServletRequest request) {
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);
			
			request.setAttribute("title", "Surprizer - Delete Item");
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			GiftList list = (GiftList) session.getAttribute("list");
			
			String item = request.getParameter("item");
			
			if(item != null){
				int itemId = Integer.valueOf(item);
				try {
					itemDAO.delete(itemId);
					list.decNumItems();
					giftlistDAO.modify(list);
				} catch (DAOException e) {
					errors.add(e.getMessage());
					return request.getContextPath() + "/viewList.do";
				}
			}
			return request.getContextPath() + "/viewList.do";
		
		}
	}

