package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import databeans.UsersLists;
import formbeans.EditListFormBean;
import formbeans.LoginFormBean;
import formbeans.NewItemFormBean;
import formbeans.NewListFormBean;
import formbeans.SearchListFormBean;

public class SearchListAction extends Action{
	private FormBeanFactory<SearchListFormBean> formBeanFactory = FormBeanFactory
			.getInstance(SearchListFormBean.class);
	private GiftListDAO giftListDAO;
	private UserDAO userDAO;
	
	public SearchListAction(Model model) {
		giftListDAO = model.getGiftListDAO();
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "searchLists.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		SearchListFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Surprizer - Search Lists");
		
		
		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "searchLists.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			System.out.println("Validation Errors");
			return "searchLists.jsp";
		}
		
		String searchString = form.getSearchString();
		
		User[] users = null;
		try {
			users = userDAO.lookupByNameOrEmail(searchString);
		} catch (DAOException e1) {
			errors.add(e1.getMessage());
		}
		ArrayList<GiftList> matchedLists = new ArrayList<GiftList>();
		for(User u: users){
			if(u.getUserId() == user.getUserId()) continue; //skip over own lists, cause thats weird.
			GiftList[] lists = null;
			try {
				lists = giftListDAO.getUserLists(u);
				matchedLists.addAll(Arrays.asList(lists));
			} catch (DAOException e) {
				errors.add(e.getMessage());
			}
		}
		for (Iterator<GiftList> iterator = matchedLists.iterator(); iterator.hasNext(); ) {
			  GiftList gl = iterator.next();
			  if(gl.getPrivacySetting().equals("Private")){
			    iterator.remove();
			  }
		}
		request.setAttribute("searchResults", matchedLists);
		request.setAttribute("searchString", searchString);
		
		return "searchLists.jsp";
	}
}

