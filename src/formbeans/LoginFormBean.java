package formbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mybeans.form.FormBean;


public class LoginFormBean extends FormBean {
	private String email;
	private String password;
	
	public String getEmail()  { return email; }
	public String getPassword()  { return password; }
	
	public void setEmail(String s)  { email = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setPassword(String s)  { password = StringEscapeUtils.escapeHtml4(s.trim()); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		//adding simple regular expression to validate that input is an email
		Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
		
		if (email == null || email.length() == 0) {
			errors.add("Email is required");
		}
		
		if (!regex.matcher(email).matches()) {
			errors.add("Invalid email address.");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		
		return errors;
	}
	
	public boolean isPresent() {
		if (email != null) return true;
		if (password != null) return true;
		return false;
	}
}


