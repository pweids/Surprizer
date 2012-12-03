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

import databeans.GiftList;
import databeans.User;
import formbeans.EditListFormBean;

public class EditListAction extends Action {
	private FormBeanFactory<EditListFormBean> formBeanFactory = FormBeanFactory
			.getInstance(EditListFormBean.class);
	private GiftListDAO giftlistDAO;

	public EditListAction(Model model) {
		giftlistDAO = model.getGiftListDAO();
	}

	@Override
	public String getName() {
		return "editList.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		EditListFormBean form = null;

		try {
			form = formBeanFactory.create(request);
		} catch (FormBeanException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("form", form);
		
		request.setAttribute("title", "Surprizer - Edit List");
		
		String list = request.getParameter("list");
		if(list != null){ 
			GiftList giftList = null;
			try {
				int listId = Integer.valueOf(list);
				giftList = giftlistDAO.lookup(listId);
			
				request.setAttribute("giftlist", giftList);
			} catch (DAOException e) {
				errors.add(e.getMessage());
				//return "page-editList.jsp";
				return "editList.jsp";
			}
				
			// If no params were passed, return with no errors so that the form will
			// be presented (we assume for the first time).
			if (!form.isPresent()) {
				//return "page-editList.jsp";
				return "editList.jsp";
			}
	
			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				System.out.println("Validation Errors");
				//return "page-editList.jsp";
				return "editList.jsp";
			}		
			
			GiftList modifiedList = giftList;
			
			if(form.getListName().length() > 0)
				modifiedList.setListName(form.getListName());
			
			if(form.getDescription().length() > 0)
				modifiedList.setDescription(form.getDescription());
			
			if(form.getPrivacySetting() != modifiedList.getPrivacySetting())
				modifiedList.setPrivacySetting(modifiedList.getPrivacySetting());
			System.out.println(modifiedList.getUserId() + "  " + user.getUserId());
			if(modifiedList.getUserId() == user.getUserId()){
				try {
					giftlistDAO.modify(modifiedList);
				} catch (DAOException e) {
					errors.add(e.getMessage());
					//return "page-editList.jsp";
					return "editList.jsp";
				}
			}
		}
		return request.getContextPath() + "/yourLists.do";
	}
}
