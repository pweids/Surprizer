package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import org.mybeans.form.FormBean;

public class SearchListFormBean extends FormBean {
	String searchString;
	
	public String getSearchString()   { return searchString;   }
	
	public void setSearchString(String s) { searchString = StringEscapeUtils.escapeHtml4(s.trim()); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (searchString == null || searchString.length() == 0) {
			errors.add("Name is required");
		}
		
		return errors;
	}
	

	public boolean isPresent() {
		if (searchString != null) return true;
		return false;
	}
	
}
