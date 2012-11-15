package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class EditUserFormBean extends FormBean {
	private String name;
	private String password;
	private String passwordConfirm;
	
	public String getName() { return name; }
	public String getPassword()  { return password;  }
	public String getPasswordConfirm()   { return passwordConfirm;   }
	
	public void setName(String s) { name = s.trim(); }
	public void setPassword(String s)  { password = s.trim();  }
	public void setPasswordConfirm(String s) { passwordConfirm = s.trim(); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (name == null || name.length() == 0) {
			errors.add("Name is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}

		if (passwordConfirm == null || passwordConfirm.length() == 0) {
			errors.add("Confirm Password is required");
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
		if (password  != null) return true;
		if (passwordConfirm   != null) return true;
		return false;
	}
}
