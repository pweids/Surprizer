package formbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mybeans.form.FormBean;

public class RegisterFormBean extends FormBean {
	private String name;
	private String email;
	private String password;
	private String passwordConfirm;
	
	public String getName() { return name; }
	public String getEmail()  { return email;  }
	public String getPassword()  { return password;  }
	public String getPasswordConfirm()   { return passwordConfirm;   }
	
	public void setName(String s) { name = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setEmail(String s) { email = StringEscapeUtils.escapeHtml4(s.trim());  }
	public void setPassword(String s)  { password = StringEscapeUtils.escapeHtml4(s.trim());  }
	public void setPasswordConfirm(String s) { passwordConfirm = StringEscapeUtils.escapeHtml4(s.trim()); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		//Another email-checking regex
		Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
		
		if (name == null || name.length() == 0) {
			errors.add("Name is required");
		}

		if (email == null || email.length() == 0) {
			errors.add("Email is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}

		if (passwordConfirm == null || passwordConfirm.length() == 0) {
			errors.add("Confirmation Password is required");
		}
		
		if (!regex.matcher(email).matches()) {
			errors.add("Invalid email address.");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (!password.equals(passwordConfirm)) {
			errors.add("Passwords are not the same");
		}
		return errors;
	}
	
	public boolean isPresent() {
		if (name != null) return true;
		if (email  != null) return true;
		if (password  != null) return true;
		if (passwordConfirm   != null) return true;
		return false;
	}
}
