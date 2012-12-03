package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Emailer.SendMail;


import databeans.User;
import formbeans.LoginFormBean;

public class VerifyEmailAction extends Action {
	private UserDAO userDAO;

	public VerifyEmailAction(Model model) {
		userDAO = model.getUserDAO();
	}
	
	@Override
	public String getName() {
		return "verifyEmail.do";
	}

	
	@Override
	public String perform(HttpServletRequest request) {
	
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		request.setAttribute("title", "Surprizer - Account Verification");
		
		String email = request.getParameter("email");
		if(email != null){
			try {
				user = userDAO.lookup(email);
			} catch (DAOException e) {
				errors.add(e.getMessage());
			}
			System.out.println("Session user is null, check parameter.");
			
		}
		else System.out.println("email is nukk.");
		if(user==null) System.out.println("user is null.");
			if(user.isEmailVerified()){
				if(request.getContextPath() == null) System.out.println("request is null");
				return request.getContextPath() + "/yourLists.do";
			}
			String subject = "Thank you for registering on Surprizer";
			String message = "Click the following link to verify your account <br />" +
							 "<form action=\"http://localhost:8080/Surprizer/verifyEmail.do\" method=POST>" + 
							 "	<input type=\"hidden\" name=\"email\" value =\""+ user.getEmail() + "\" >" +
							 "	<input type=\"hidden\" name=\"verify\" value =\""+ user.getVerified() + "\" >" +
							 "	<input type=\"submit\" value=\"Verify your Email\" />" + 	
							 "</form>";
		
	
		// If the verify param was not passed, send the verification email 
		// and return to the verify page
		String verification = request.getParameter("verify");
		if (verification == null) {
			 SendMail.send("verify@surprizer.com", user.getEmail(), subject, message);
			return "page-verifyEmail.jsp";
		}

		// Check the hash code
		if (verification.equals(user.getVerified())) {
			user.setVerified("Verified");
			return request.getContextPath() + "/yourLists.do";
		}
		else{
			errors.add("Unable to verify.  Please click resend to try again");
			return "page-verifyEmail.jsp";		
		}
	}	
}
