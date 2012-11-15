package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.GiftListDAO;
import model.Model;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.User;
import formbeans.LoginFormBean;

public class YourListsAction extends Action{
		private GiftListDAO giftListDAO;
		
		public YourListsAction(Model model) {
			giftListDAO = model.getGiftListDAO();
		}

		@Override
		public String getName() {
			return "yourLists.do";
		}

		@Override
		public String perform(HttpServletRequest request) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);

			request.setAttribute("title", "Suprizer Lists");

			databeans.GiftList[] userLists = null;
			try {
				userLists = giftListDAO.getUserLists(user);
				//Get Following Lists
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("UserLists", userLists);
			
			return "page-yourLists.jsp";
			
			//if(user.isVerified())
			//	return "page-yourLists.jsp";
			//else
			//	return request.getContextPath() + "/verifyEmail.do";

		}
	}

