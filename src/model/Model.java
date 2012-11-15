package model;

import javax.servlet.*;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanTable;

public class Model {

	private UserDAO userDAO;
	private GiftListDAO giftListDAO;
	
	public Model(ServletConfig config) throws ServletException {
		String jdbcDriver   = config.getInitParameter("jdbcDriverName");
		String jdbcURL      = config.getInitParameter("jdbcURL");
		String jdbcUser     = config.getInitParameter("jdbcUser");
		String jdbcPassword = config.getInitParameter("jdbcPassword");
		if(jdbcUser == null) jdbcUser = "";
		if(jdbcPassword == null) jdbcPassword = "";
		
		BeanTable.useJDBC(jdbcDriver, jdbcURL, jdbcUser, jdbcPassword);

		try {
			userDAO    = new UserDAO();
			giftListDAO	   = new GiftListDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}	
		
	}	
    public UserDAO getUserDAO() {  return userDAO;  }
    public GiftListDAO getGiftListDAO() {  return giftListDAO;  }

}