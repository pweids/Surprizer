package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.User;

import model.Model;

public class LogoutAction extends Action{
	 
    public LogoutAction(Model model) {
    }
    
    @Override
	public String getName() {  return "logout.do"; }

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
        session.removeAttribute("user");

		return request.getContextPath() + "/login.do";
    }
}
